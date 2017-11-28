package org.sms.opensaml.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sms.SysConstants;
import org.sms.opensaml.service.SamlService;
import org.sms.project.security.SampleAuthenticationManager;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理SAML请求的数据（SSO数据验证）
 * 
 * @author Sunny
 */
@Controller
@RequestMapping("/SAML2")
public class SamlController {

  @Autowired
  private SamlService samlService;

  @Autowired
  private UserService userService;

  /**
   * SP 接受IDP端的Artifact
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/receiveArtifact")
  public String receiveIDPArtifact(HttpServletRequest request, HttpServletResponse response) {
    String spArtifact = request.getParameter(SysConstants.ARTIFACT_KEY);
    User user = samlService.getUserByAssertion(spArtifact);
    if (Objects.nonNull(user)) {
      request.getSession().setAttribute(SysConstants.LOGIN_USER, user);
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