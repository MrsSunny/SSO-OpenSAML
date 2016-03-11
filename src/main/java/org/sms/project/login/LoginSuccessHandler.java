package org.sms.project.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.Response;
import org.sms.SysConstants;
import org.sms.component.idfactory.UUIDFactory;
import org.sms.organization.user.entity.User;
import org.sms.project.helper.SessionHelper;
import org.sms.project.sso.SSOHelper;
import org.sms.saml.service.SamlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zhenxing.Liu
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
  
  @Autowired
  private SamlService samlService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    HttpSession session = request.getSession();
    final Artifact idpArtifact = samlService.buildArtifact();
    Object url = session.getAttribute(SysConstants.ACTION_KEY);
    if (url == null) {
      url = SysConstants.DEFAULT_SP_RECEIVESPARTIFACT_URL;
    }
    final Response samlResponse = samlService.buildResponse(UUIDFactory.INSTANCE.getUUID());
    User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
    samlService.addAttribute(samlResponse, user);
    SSOHelper.INSTANCE.put(idpArtifact.getArtifact(), samlResponse);
    response.sendRedirect(url.toString() + SysConstants.METHOD_SPILT_CHAR + SysConstants.ARTIFACT_KEY + SysConstants.PARAM_VALUE + samlService.buildXMLObjectToString(idpArtifact));
  }
}