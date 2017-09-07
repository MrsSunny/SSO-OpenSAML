package org.sms.opensaml.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletInputStream;
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
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.SysConstants;
import org.sms.core.id.UUIDFactory;
import org.sms.opensaml.service.SamlService;
import org.sms.project.app.entity.App;
import org.sms.project.app.service.AppService;
import org.sms.project.encrypt.rsa.RSACoder;
import org.sms.project.helper.AuthenRequestHelper;
import org.sms.project.helper.CertificateHelper;
import org.sms.project.helper.SSOHelper;
import org.sms.project.helper.SessionHelper;
import org.sms.project.security.SampleAuthenticationManager;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.sms.project.util.HttpUtil;
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

  private Logger logger = LoggerFactory.getLogger(SamlController.class.getName());

  /**
   * IDP 接受SP端的Artifact
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveSPArtifact")
  public String receiveSPArtifact(Model model, HttpServletRequest request, HttpServletResponse response) {

    // 获取Artifact
    String artifactBase64 = request.getParameter(SysConstants.ARTIFACT_KEY);
    Objects.requireNonNull(artifactBase64, "应用参数错误，artifact数据不能为空");
    final ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    final Artifact artifact = (Artifact) samlService.buildStringToXMLObject(artifactBase64);
    artifactResolve.setArtifact(artifact);
    // 对artifactResolve对象进行签名操作
    samlService.signXMLObject(artifactResolve);
    String artifactParam = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = null;
    //根据Artifact信息从SP中获取Auth Request信息
    try {
      postResult = HttpUtil.doPost(SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE, artifactParam);
    } catch (Exception e) {
      throw new RuntimeException("访问SP端的：" + SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE + "服务错误" + e.getMessage());
    }
    if (null == postResult || "".equals(postResult)) {
      return "redirect:/login.html";
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
      throw new RuntimeException("无法获取AuthRequest数据，认证错误");
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
      return "redirect:/login.html";
    }
    
    Subject subject = authnRequest.getSubject();
    NameID nameID = subject.getNameID();
    String ticket = nameID.getValue();
    if ("".equals(ticket) || null == ticket) {
      return "redirect:/login.html";
    }
    try {
      ticket = URLDecoder.decode(ticket, SysConstants.CHARSET);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    /**
     * 解密ticket
     */
    PrivateKey privateKey = CertificateHelper.getRSAPrivateKey();
    String[] afterDecode = null;
    try {
      byte[] ticketArray = Base64.decode(ticket);
      byte[] decryptTicketArray = RSACoder.INSTANCE.decryptByPrivateKey(privateKey, ticketArray);
      String decryptTicket = new String(decryptTicketArray);
      afterDecode = decryptTicket.split(SysConstants.TICKET_SPILT);
    } catch (Exception e) {
      return "redirect:/login.html";
    }
    logger.debug("Ticket验证痛过");
    // 判断令牌是否过期，如果令牌过期则直接
    if (afterDecode == null || afterDecode.length != 3) {
      return "redirect:/login.html";
    }
    long expireTime = Long.parseLong(afterDecode[2]);
    long nowTime = System.currentTimeMillis();
    if (expireTime < nowTime) {
      logger.debug("Token已过期");
      return "redirect:/login.html";
    }
    final Artifact idpArtifact = samlService.buildArtifact();
    final Response samlResponse = samlService.buildResponse(UUIDFactory.INSTANCE.getUUID());
    long id = Long.parseLong(afterDecode[0]);
    User user = new User();
    user.setId(id);
    user.setEmail(afterDecode[1]);
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
      return samlService.buildXMLObjectToString(artifactResponse);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * SP 接受SP端的Artifact
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
    if (null == postResult) {
      throw new RuntimeException("从" + SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE + "服务获取的数据为空，请检查IDP端数据格式");
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
      /**
       * 验证签名
       */
      boolean signSuccess = samlService.validate(assertion);
      if (!signSuccess) {
        throw new RuntimeException("验证签名错误");
      }
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
        } else if (name.equals("Email")) {
          user.setEmail(valueString);
        }
      });
      session.setAttribute(SysConstants.LOGIN_USER, user);
      putAuthnToSecuritySession("admin", "admin");
      request.setAttribute(SysConstants.ERROR_LOGIN, false);
    }
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
    String ticket = (String) SessionHelper.get(request, SysConstants.SSO_TOKEN_KEY);
    AuthnRequest authnRequest = samlService.buildAuthnRequest(ticket, SysConstants.SPRECEIVESPARTIFACT_URL);
    //把该request放入到authnrequest的全局变量里面
    //把artifact 和 authnRequest 绑定在一起，供后面的根据Artifact获取authnRequest做准备
    AuthenRequestHelper.INSTANCE.put(artifact.getArtifact(), authnRequest);
    return "/saml/sp/send_artifact_to_idp";
  }
  
  @RequestMapping("/sendResponse")
  public String sendResponse(HttpServletRequest request, HttpServletResponse response) {
	  
	  return "/saml/sp/send_artifact_to_idp";
  }
}