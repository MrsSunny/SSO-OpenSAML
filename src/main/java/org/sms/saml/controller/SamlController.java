package org.sms.saml.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Response;
import org.sms.SysConstants;
import org.sms.component.idfactory.UUIDFactory;
import org.sms.organization.user.entity.User;
import org.sms.project.security.SysUserDetailsService;
import org.sms.saml.service.SamlService;
import org.sms.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
  private SysUserDetailsService sysUserDetailsService;

  /**
   * IDP
   * 接受SP端的Artifact
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveSPArtifact")
  public String receiveSPArtifact(HttpServletRequest request, HttpServletResponse response) {
    String _sp_artifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    Artifact artifact = (Artifact)samlService.buildStringToXMLObject(_sp_artifact);
    artifactResolve.setArtifact(artifact);
    samlService.signXMLObject(artifactResolve);
    String requestStr = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = HttpUtil.doPost(SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE, requestStr);
    ArtifactResponse artifactResponse = (ArtifactResponse) samlService.buildStringToXMLObject(postResult);
    AuthnRequest authnRequest = (AuthnRequest)artifactResponse.getMessage();
    System.out.println(authnRequest);
    Artifact idpArtifact = samlService.buildArtifact();
    request.setAttribute(SysConstants.ARTIFACT_KEY, samlService.buildXMLObjectToString(idpArtifact));
    return "/saml/idp/send_artifact_to_sp";
  }
  
  /**
   * IDP
   * 接受SP端的Artifact
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
   * SP
   * 接受SP端的Artifact
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveIDPArtifact")
  public String receiveIDPArtifact(HttpServletRequest request, HttpServletResponse response) {
    String _sp_artifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    ArtifactResolve artifactResolve = samlService.buildArtifactResolve();
    Artifact artifact = (Artifact)samlService.buildStringToXMLObject(_sp_artifact);
    artifactResolve.setArtifact(artifact);
    samlService.signXMLObject(artifactResolve);
    String requestStr = samlService.buildXMLObjectToString(artifactResolve);
    String postResult = HttpUtil.doPost(SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE, requestStr);
    ArtifactResponse artifactResponse = (ArtifactResponse) samlService.buildStringToXMLObject(postResult);
    Response samlResponse = (Response)artifactResponse.getMessage();
    System.out.println(samlResponse);
    request.setAttribute(SysConstants.ARTIFACT_KEY, "验证成功");
    HttpSession session = request.getSession(true);
    User user = new User();
    user.setId(1L);
    user.setName("admin");
    user.setLogin_id("admin");
    session.setAttribute(SysConstants.LOGIN_USER, user);
    return "/saml/sp/redirect";
  }
  
  /**
   * SP
   * 接受SP端的Artifact
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
      ArtifactResolve artifactResolve = (ArtifactResolve) samlService.buildStringToXMLObject(result.trim());
      ArtifactResponse artifactResponse = samlService.buildArtifactResponse();
      artifactResponse.setInResponseTo(artifactResolve.getID());
      artifactResponse.setMessage(samlService.buildAuthnRequest());
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
   * SP
   * 生成SP端的Artifact
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/sendArtifactToIDP")
  public String sendSPArtifact(HttpServletRequest request, HttpServletResponse response) {
    Artifact artifact = samlService.buildArtifact();
    String artifactBase64 = samlService.buildXMLObjectToString(artifact);
    String token = UUIDFactory.INSTANCE.getUUID();
    request.setAttribute(SysConstants.ARTIFACT_KEY, artifactBase64);
    request.setAttribute(SysConstants.TOKEN_KEY, token);
    return "/saml/sp/send_artifact_to_idp";
  }
}