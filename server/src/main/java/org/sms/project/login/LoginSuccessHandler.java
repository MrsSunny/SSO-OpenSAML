package org.sms.project.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.Response;
import org.sms.SysConstants;
import org.sms.core.id.UUIDFactory;
import org.sms.opensaml.service.SamlService;
import org.sms.project.helper.SSOHelper;
import org.sms.project.helper.SessionHelper;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author Sunny
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
  
  @Autowired
  private SamlService samlService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    final Artifact idpArtifact = samlService.buildArtifact();
    final Response samlResponse = samlService.buildResponse(UUIDFactory.INSTANCE.getUUID());
    
    String id = request.getSession().getId();
    System.out.println(id);
    User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
    samlService.addAttribute(samlResponse, user);
    SSOHelper.INSTANCE.put(idpArtifact.getArtifact(), samlResponse);
    response.sendRedirect("/SAML2/sendResponse" + SysConstants.METHOD_SPILT_CHAR + SysConstants.ARTIFACT_KEY + SysConstants.PARAM_VALUE + samlService.buildXMLObjectToString(idpArtifact));
  }
}