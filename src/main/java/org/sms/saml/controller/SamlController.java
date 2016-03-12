package org.sms.saml.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.xml.schema.XSString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.SysConstants;
import org.sms.component.idfactory.UUIDFactory;
import org.sms.organization.user.entity.User;
import org.sms.organization.user.service.UserService;
import org.sms.project.app.entity.App;
import org.sms.project.app.service.AppService;
import org.sms.project.authentication.entity.SysAuthentication;
import org.sms.project.authentication.service.SysAuthenticationService;
import org.sms.project.helper.SessionHelper;
import org.sms.project.security.SampleAuthenticationManager;
import org.sms.project.sso.AuthenRequestHelper;
import org.sms.project.sso.SSOHelper;
import org.sms.saml.service.SamlService;
import org.sms.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理SAML请求的数据（SSO数据验证）
 * @author Sunny
 */
@Controller
@RequestMapping("/SAML2")
public class SamlController {

  @Autowired
  private SamlService samlService;

  @Autowired
  private AppService appService;

  @Autowired
  private UserService userService;

  @Autowired
  private SysAuthenticationService sysAuthenticationService;

  private Logger logger = LoggerFactory.getLogger(SamlController.class.getName());

  /**
   * IDP 接受SP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveSPArtifact")
  public String receiveSPArtifact(Model model, HttpServletRequest request, HttpServletResponse response) {

    // 获取Artifact
    String artifactBase64 = request.getParameter(SysConstants.ARTIFACT_KEY);
    if (null == artifactBase64) {
      throw new RuntimeException("SP端传递的Artifact参数错误，该参数不可为空");
    }
    final ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    final Artifact artifact = (Artifact) samlService.buildStringToXMLObject(artifactBase64);
    artifactResolve.setArtifact(artifact);
    // 对artifactResolve对象进行签名操作
    samlService.signXMLObject(artifactResolve);
    String artifactParam = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = null;
    try {
      postResult = HttpUtil.doPost(SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE, artifactParam);
    } catch (Exception e) {
      throw new RuntimeException("访问SP端的：" + SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE + "服务错误" + e.getMessage());
    }
    final ArtifactResponse artifactResponse = (ArtifactResponse) samlService.buildStringToXMLObject(postResult);
    final Status status = artifactResponse.getStatus();
    if (null == status) {
      throw new RuntimeException("客户端的Status不能为空");
    }
    StatusCode statusCode = status.getStatusCode();
    if (null == statusCode) {
      throw new RuntimeException("无法获取statusCode");
    }
    String codeValue = statusCode.getValue();
    // 判断SAML的StatusCode
    if (codeValue == null || !codeValue.equals(StatusCode.SUCCESS_URI)) {
      throw new RuntimeException("无法获取codeValue， 认证失败， SP端数据错误");
    }
    final String inResponseTo = artifactResponse.getInResponseTo();
    final String artifactResolveID = artifactResolve.getID();
    if (null == inResponseTo || !inResponseTo.equals(artifactResolveID)) {
      logger.error("artifact的值和接收端的inResponseTo的值不一致，认证错误");
      throw new RuntimeException("artifact的值和接收端的inResponseTo的值不一致，认证错误");
    }
    final AuthnRequest authnRequest = (AuthnRequest) artifactResponse.getMessage();
    if (authnRequest == null) {
      throw new RuntimeException("artifact的值和接收端的inResponseTo的值不一致，认证错误");
    }
    // 获取SP的消费URL，下一步回调需要用到
    final String customerServiceUrl = authnRequest.getAssertionConsumerServiceURL();
    if (null == customerServiceUrl) {
      throw new RuntimeException("无法获取customerServiceUrl，SP端数据错误");
    }
    request.setAttribute(SysConstants.ACTION_KEY, customerServiceUrl);
    HttpSession session = request.getSession(false);
    session.setAttribute(SysConstants.ACTION_KEY, customerServiceUrl);
    final SAMLVersion samlVersion = authnRequest.getVersion();

    // 判断版本是否支持
    if (null == samlVersion || !SAMLVersion.VERSION_20.equals(samlVersion)) {
      throw new RuntimeException("SAML版本错误，只支持2.0");
    }

    final Issuer issuer = authnRequest.getIssuer();
    final String appName = issuer.getValue();

    // 判断issure的里面的值是否在SSO系统中注册过
    final App app = appService.findAppByAppName(appName.trim());
    if (app == null) {
      throw new RuntimeException("不支持当前系统: " + appName);
    }
    final String requestID = authnRequest.getID();
    logger.debug("AuthRequest的ID为：" + requestID);
    // 根据AuthnRequest判断用户是否登录
    // 判断令牌是否存在,如果令牌不存在则直接跳转到登录页面
    final SysAuthentication sysAuthen = sysAuthenticationService.queryBySSOToken(requestID);
    if (sysAuthen.getSso_token() == null) {
      throw new RuntimeException("IDP端错误，无法获取SSO_TOKEN");
    }
    // 判断令牌是否过期，如果令牌过期则直接
    Timestamp expireTimestamp = sysAuthen.getExpire_time();

    if (null == expireTimestamp) {
      throw new RuntimeException("IDP端错误，无法获取过期时间");
    }
    long expireTime = expireTimestamp.getTime();
    long nowTime = System.currentTimeMillis();
    if (expireTime > nowTime) {
      logger.debug("Token已过期");
      return "redirect:/loginPage";
    }
    final Artifact idpArtifact = samlService.buildArtifact();
    final Response samlResponse = samlService.buildResponse(UUIDFactory.INSTANCE.getUUID());
    String userid = sysAuthen.getSubject_id();
    if (userid == null) {
      throw new RuntimeException("无法在认证纪录里获取Subject信息");
    }
    long id = Long.parseLong(userid);
    User user = userService.find(id);
    if (user == null) {
      logger.debug("无法根据认证纪录来获取用户信息");
      return "redirect:/loginPage";
    }
    samlService.addAttribute(samlResponse, user);
    SSOHelper.INSTANCE.put(idpArtifact.getArtifact(), samlResponse);
    request.setAttribute(SysConstants.ARTIFACT_KEY, samlService.buildXMLObjectToString(idpArtifact));
    return "/saml/idp/send_artifact_to_sp";
  }

  /**
   * IDP 接受SP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @ResponseBody
  @RequestMapping("/IDPArtifactResolution")
  public String IDPArtifactResolution(HttpServletRequest request, HttpServletResponse response) {
    ServletInputStream inputStream;
    try {
      inputStream = request.getInputStream();
      String result = HttpUtil.readInputStream(inputStream);
      ArtifactResolve artifactResolve = (ArtifactResolve) samlService.buildStringToXMLObject(result.trim());
      ArtifactResponse artifactResponse = samlService.buildArtifactResponse();
      artifactResponse.setInResponseTo(artifactResolve.getID());
      Artifact artifact = artifactResolve.getArtifact();
      Response samlResponse = SSOHelper.INSTANCE.get(artifact.getArtifact());
      if (null == samlResponse) {
        return null;
      }
      artifactResponse.setMessage(samlResponse);
      SSOHelper.INSTANCE.remove(artifact.getArtifact());
      SysAuthentication sysAuthen = new SysAuthentication();
      sysAuthen.setSso_token(samlResponse.getID());
      sysAuthen.setId(System.currentTimeMillis());
      sysAuthen.setSubject_id(1 + "");
      sysAuthenticationService.create(sysAuthen);
      return samlService.buildXMLObjectToString(artifactResponse);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * SP 接受SP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveIDPArtifact")
  public String receiveIDPArtifact(HttpServletRequest request, HttpServletResponse response) {
    String spArtifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    if (null == spArtifact) {
      throw new RuntimeException("无法获取IDP端传过来的Artifact");
    }
    ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    Artifact artifact = (Artifact) samlService.buildStringToXMLObject(spArtifact);
    artifactResolve.setArtifact(artifact);
    samlService.signXMLObject(artifactResolve);
    String requestStr = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = null;
    try {
      postResult = HttpUtil.doPost(SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE, requestStr);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("访问IDP的" + SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE + "服务错误");
    }
    ArtifactResponse artifactResponse = (ArtifactResponse) samlService.buildStringToXMLObject(postResult);
    SAMLObject samlObject = artifactResponse.getMessage();
    if (null == samlObject) {
      throw new RuntimeException("无法获取Response信息");
    }
    Response samlResponse = (Response) samlObject;
    List<Assertion> assertions = samlResponse.getAssertions();
    if (null == assertions || assertions.size() == 0) {
      throw new RuntimeException("无法获取断言，请重新发起请求！！！");
    }
    Assertion assertion = samlResponse.getAssertions().get(0);
    if (assertion == null) {
      request.setAttribute(SysConstants.ERROR_LOGIN, true);
    } else {
      // if (!isSigned) {
      // request.setAttribute(SysConstants.ERROR_LOGIN, true);
      // } else {
      HttpSession session = request.getSession(false);

      List<AttributeStatement> arrtibuteStatements = assertion.getAttributeStatements();
      if (null == arrtibuteStatements || arrtibuteStatements.size() == 0) {
        throw new RuntimeException("无法获取属性列表，请重新发起请求");
      }
      AttributeStatement attributeStatement = assertion.getAttributeStatements().get(0);
      List<Attribute> list = attributeStatement.getAttributes();
      if (null == list) {
        throw new RuntimeException("无法获取属性列表IDP端错误");
      }
      User user = new User();
      list.forEach(pereAttribute -> {
        String name = pereAttribute.getName();
        XSString value = (XSString) pereAttribute.getAttributeValues().get(0);
        String valueString = value.getValue();
        if (name.endsWith("Name")) {
          user.setName(valueString);
        } else if (name.equals("Id")) {
          user.setId(Long.parseLong(valueString));
        } else if (name.equals("LoginId")) {
          user.setLogin_id(valueString);
        } else if (name.equals("Email")) {
          user.setEmail(valueString);
        }
      });
      addSSOCookie(response, samlResponse.getID());
      session.setAttribute(SysConstants.LOGIN_USER, user);
      putAuthnToSecuritySession("admin", "admin");
      request.setAttribute(SysConstants.ERROR_LOGIN, false);
    }
    // }
    return "/saml/sp/redirect";
  }

  public void putAuthnToSecuritySession(String name, String password) {
    final List<String> list = new ArrayList<String>();
    list.add("ADMIN");
    AuthenticationManager authenticationManager = new SampleAuthenticationManager(list);
    Authentication request = new UsernamePasswordAuthenticationToken(name, password);
    Authentication result = authenticationManager.authenticate(request);
    SecurityContextHolder.getContext().setAuthentication(result);
  }

  /**
   * SP 接受IDP端的Artifact 并返回给IDP ArtifactResponse 接受SP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @ResponseBody
  @RequestMapping("/SPArtifactResolution")
  public String SPArtifactResolution(HttpServletRequest request, HttpServletResponse response) {
    try {
      ServletInputStream inputStream = request.getInputStream();
      String result = HttpUtil.readInputStream(inputStream);
      ArtifactResponse artifactResponse = samlService.buildArtifactResponse();

      /**
       * 验证签名
       */
      boolean isSign = samlService.validate(result.trim());
      if (!isSign) {
        /**
         * 添加认证失败状态
         */
        artifactResponse.setStatus(samlService.getStatusCode(false));
      } else {
        ArtifactResolve artifactResolve = (ArtifactResolve) samlService.buildStringToXMLObject(result.trim());
        artifactResponse.setInResponseTo(artifactResolve.getID());
        artifactResponse.setStatus(samlService.getStatusCode(true));
        Artifact artifact = artifactResolve.getArtifact();
        AuthnRequest authnRequest = AuthenRequestHelper.INSTANCE.get(artifact.getArtifact());
        if (null == authnRequest) {
          return null;
        }
        artifactResponse.setMessage(authnRequest);
        artifactResponse.setInResponseTo(artifactResolve.getID());
      }
      return samlService.buildXMLObjectToString(artifactResponse);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * SP 发送Artifact到IDP 生成SP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/sendArtifactToIDP")
  public String sendSPArtifact(HttpServletRequest request, HttpServletResponse response) {

    // 构建默认的artifact
    Artifact artifact = samlService.buildArtifact();
    String artifactBase64 = samlService.buildXMLObjectToString(artifact);
    String token = UUIDFactory.INSTANCE.getUUID();
    SessionHelper.put(request, artifact.getArtifact(), artifact);
    // 把压缩和Base64的artifact传递到页面
    request.setAttribute(SysConstants.ARTIFACT_KEY, artifactBase64);
    request.setAttribute(SysConstants.TOKEN_KEY, token);
    // 如果能在Cookie中获取到idp_token 在把这个ID选择为authnRequest的ID
    String sso_token_key = (String) SessionHelper.get(request, SysConstants.SSO_TOKEN_KEY);
    if (null == sso_token_key) {
      sso_token_key = SysConstants.SAML_ID_PREFIX_CHAR + UUIDFactory.INSTANCE.getUUID();
    }
    AuthnRequest authnRequest = samlService.buildAuthnRequest(sso_token_key, SysConstants.SPRECEIVESPARTIFACT_URL);
    // 把该request放入到authnrequest的全局变量里面，供后面的根据Artifact获取authnRequest做准备
    AuthenRequestHelper.INSTANCE.put(artifact.getArtifact(), authnRequest);
    return "/saml/sp/send_artifact_to_idp";
  }

  public void addSSOCookie(HttpServletResponse response, String string) {
    Cookie cookie = new Cookie(SysConstants.SSO_TOKEN_KEY, string);
    cookie.setDomain(".soaer.com");
    cookie.setPath("/");
    cookie.setMaxAge(24 * 60 * 60);
    response.addCookie(cookie);
  }
}