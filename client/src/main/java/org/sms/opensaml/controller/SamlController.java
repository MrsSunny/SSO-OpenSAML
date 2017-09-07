package org.sms.opensaml.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.SysConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.schema.XSString;
import org.sms.opensaml.service.SamlService;
import org.sms.project.app.service.AppService;
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
import org.springframework.web.bind.annotation.RequestMapping;

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
   * SP 接受IDP端的Artifact
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveArtifact")
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
}