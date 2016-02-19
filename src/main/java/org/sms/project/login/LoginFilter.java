package org.sms.project.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.core.AuthnRequest;
import org.sms.organization.user.entity.User;
import org.sms.organization.user.service.UserService;
import org.sms.project.encrypt.md5.MD5;
import org.sms.project.filter.SamlFilter;
import org.sms.saml.service.SamlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private static final String LOCKED = "0";

  @Autowired
  private UserService userService;

  @Autowired
  private SamlService samlService;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    if (!request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }

    String authnRequest = request.getParameter(SamlFilter.AUTHREQUEST);

    if (authnRequest == null)
      try {
        response.sendRedirect("/login.jsp?errorMessage=无法找到SSO服务器请求体");
      } catch (IOException e) {
        e.printStackTrace();
      }

    /**
     * 获取AuthnRequest 对象
     */
    AuthnRequest authnRequestBean = samlService.getAuthRequest(authnRequest);
    if (null == authnRequestBean)
      try {
        response.sendRedirect("/login.jsp?errorMessage=验证错误");
      } catch (IOException e) {
        e.printStackTrace();
      }
    /**
     * 获取服务器的URL
     */
    String serverUrl = authnRequestBean.getAssertionConsumerServiceURL();
    if (serverUrl == null)
      try {
        response.sendRedirect("/login.jsp?errorMessage=无法找到请求资源服务器地址");
      } catch (IOException e) {
        e.printStackTrace();
      }
    /**
     * 校验URL是否是合法的URL
     */
    boolean checkServerUrl = samlService.checkUrl(serverUrl);
    if (!checkServerUrl)
      try {
        response.sendRedirect("/login.jsp?errorMessage=请求服务器资源的URL不存在SSO系统，请联系管理员");
      } catch (IOException e) {
        e.printStackTrace();
      }

    String username = obtainUsername(request);
    String password = obtainPassword(request);
    if (null == username || null == password || username.equals(""))
      throw new AuthenticationServiceException("用户名或者密码为空！");
    username = username.trim();
    User user = this.userService.findUserByLogin_Id(username);
    if (null == user)
      throw new AuthenticationServiceException("用户不存在！");
    if (LOCKED.equals(user.getUsable_status()))
      throw new AuthenticationServiceException("用户已经被锁定！");
    String salt = username + password;
    salt = MD5.encrypt(salt);
    if (!salt.equals(user.getPassword()))
      throw new AuthenticationServiceException("用户名或者密码错误！");
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, salt);
    setDetails(request, authRequest);
    return this.getAuthenticationManager().authenticate(authRequest);
  }
}