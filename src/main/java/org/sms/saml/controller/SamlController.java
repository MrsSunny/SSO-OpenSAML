package org.sms.saml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sms.SysConstants;
import org.sms.component.idfactory.UUIDFactory;
import org.sms.saml.service.SamlService;
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

  @RequestMapping("/Artifact")
  public String checkTicket(HttpServletRequest request, HttpServletResponse response) {
    return "redirect:/login.jsp";
  }
  
  @RequestMapping("/buildSPArtifact")
  public String buildIdpRequest(HttpServletRequest request, HttpServletResponse response) {
    String artifact = samlService.buildArtifact();
    String token = UUIDFactory.INSTANCE.getUUID();
    request.setAttribute(SysConstants.ARTIFACT_KEY, artifact);
    request.setAttribute(SysConstants.TOKEN_KEY, token);
    return "/saml/build_sp_artifact";
  }
  
  @RequestMapping("/IDPReceiveSPArtifact")
  public String IDPReceiveSPArtifact(HttpServletRequest request, HttpServletResponse response) {
    String _sp_artifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    String _sp_token = request.getParameter(SysConstants.TOKEN_KEY);
    request.setAttribute(SysConstants.ARTIFACT_KEY, _sp_artifact);
    request.setAttribute(SysConstants.TOKEN_KEY, _sp_token);
    return "/saml/build_sp_artifact";
  }
}