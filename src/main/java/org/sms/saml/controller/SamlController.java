package org.sms.saml.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.sms.SysConstants;
import org.sms.component.idfactory.UUIDFactory;
import org.sms.organization.user.entity.User;
import org.sms.project.app.entity.App;
import org.sms.project.app.service.AppService;
import org.sms.project.helper.SessionHelper;
import org.sms.saml.service.SamlService;
import org.sms.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 * @since 1.8.0
 */
@Controller
@RequestMapping("/SAML2")
public class SamlController {

  @Autowired
  private SamlService samlService;
  
  @Autowired
  private AppService appService;

  /**
   * IDP 接受SP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveSPArtifact")
  public String receiveSPArtifact(HttpServletRequest request, HttpServletResponse response) {
    String _sp_artifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    if (null == _sp_artifact) {
      throw new RuntimeException("artifact不能为空");
    }
    final ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    final Artifact artifact = (Artifact) samlService.buildStringToXMLObject(_sp_artifact);
    artifactResolve.setArtifact(artifact);
    samlService.signXMLObject(artifactResolve);
    String requestStr = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = HttpUtil.doPost(SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE, requestStr);
    ArtifactResponse artifactResponse = (ArtifactResponse) samlService.buildStringToXMLObject(postResult);
    Status status = artifactResponse.getStatus();
    Objects.requireNonNull(status);
    StatusCode statusCode = status.getStatusCode();
    Objects.requireNonNull(statusCode);
    String codeValue = statusCode.getValue();
    if (codeValue == null || !codeValue.equals(StatusCode.SUCCESS_URI)) {
      throw new RuntimeException("认证失败");
    }
    final String inResponseTo = artifactResponse.getInResponseTo();
    final String artifactResolveID = artifactResolve.getID();
    if (null == inResponseTo || !inResponseTo.equals(artifactResolveID)) {
      throw new RuntimeException("认证失败");
    }
    final AuthnRequest authnRequest = (AuthnRequest) artifactResponse.getMessage();
    /**
     * 获取需要跳转的SP的Action
     */
    final String customerServiceUrl = authnRequest.getAssertionConsumerServiceURL();
    request.setAttribute(SysConstants.ACTION_KEY, customerServiceUrl);
    
    SAMLVersion samlVersion = authnRequest.getVersion();
    
    /**
     * 判断版本是否支持
     */
    if (null == samlVersion || !SAMLVersion.VERSION_20.equals(samlVersion)) {
      throw new RuntimeException("SAML版本错误，只支持2.0");
    }
    
    Issuer issuer = authnRequest.getIssuer();
    String appName = issuer.getValue();
    
    /**
     * 判断issure的里面的值是否在SSO系统中注册过
     */
    
    App app = appService.findAppByAppName(appName.trim());
    if (app == null) {
      throw new RuntimeException("不支持当前系统: " + appName);
    }
    final String requestID = authnRequest.getID();
    /**
     * 根据AuthnRequest判断用户是否登录
     */
    Artifact idpArtifact = samlService.buildArtifact();
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
  @RequestMapping("/IDPArtifactResolution")
  public void IDPArtifactResolution(HttpServletRequest request, HttpServletResponse response) {
    PrintWriter write = null;
    try {
      ServletInputStream inputStream = request.getInputStream();
      String result = HttpUtil.readInputStream(inputStream);
      ArtifactResolve artifactResolve = (ArtifactResolve) samlService.buildStringToXMLObject(result.trim());
      ArtifactResponse artifactResponse = samlService.buildArtifactResponse();
      artifactResponse.setInResponseTo(artifactResolve.getID());
      artifactResponse.setMessage(samlService.buildResponse(UUIDFactory.INSTANCE.getUUID(), "http://soaer.com:8888/sysuser/list"));
      write = response.getWriter();
      write.write(samlService.buildXMLObjectToString(artifactResponse));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (write != null) {
        write.close();
      }
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
    String _sp_artifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    Artifact artifact = (Artifact) samlService.buildStringToXMLObject(_sp_artifact);
    artifactResolve.setArtifact(artifact);
    samlService.signXMLObject(artifactResolve);
    String requestStr = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = HttpUtil.doPost(SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE, requestStr);
    ArtifactResponse artifactResponse = (ArtifactResponse) samlService.buildStringToXMLObject(postResult);
    Response samlResponse = (Response) artifactResponse.getMessage();
    System.out.println(samlResponse);
    request.setAttribute(SysConstants.ARTIFACT_KEY, "验证成功");
    HttpSession session = request.getSession(true);
    User user = new User();
    user.setId(1L);
    user.setName("admin");
    user.setLogin_id("admin");
    session.setAttribute(SysConstants.LOGIN_USER, user);
    putAuthnToSecuritySession("admin", "admin");
    return "/saml/sp/redirect";
  }

  public void putAuthnToSecuritySession(String name, String password) {
    AuthenticationManager am = new SampleAuthenticationManager();
    Authentication request = new UsernamePasswordAuthenticationToken(name, password);
    Authentication result = am.authenticate(request);
    SecurityContextHolder.getContext().setAuthentication(result);
  }

  /**
   * SP 接受IDP端的Artifact 并返回给IDP ArtifactResponse 接受SP端的Artifact
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/SPArtifactResolution")
  public void SPArtifactResolution(HttpServletRequest request, HttpServletResponse response) {
    PrintWriter write = null;
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
        artifactResponse.setMessage(samlService.buildAuthnRequest(SysConstants.SPRECEIVESPARTIFACT_URL));
        artifactResponse.setInResponseTo(artifactResolve.getID());
      }
      write = response.getWriter();
      write.write(samlService.buildXMLObjectToString(artifactResponse));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (write != null) {
        write.close();
      }
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
    Artifact artifact = samlService.buildArtifact();
    String artifactBase64 = samlService.buildXMLObjectToString(artifact);
    String token = UUIDFactory.INSTANCE.getUUID();
    /**
     * 把生成的Artifact放入Session
     */
    SessionHelper.put(request, artifact.getArtifact(), artifact);
    request.setAttribute(SysConstants.ARTIFACT_KEY, artifactBase64);
    request.setAttribute(SysConstants.TOKEN_KEY, token);
    return "/saml/sp/send_artifact_to_idp";
  }
}

class SampleAuthenticationManager implements AuthenticationManager {

  static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("ADMIN"));
  }

  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
  }
}