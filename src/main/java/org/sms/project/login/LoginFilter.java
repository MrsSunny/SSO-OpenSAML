package org.sms.project.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sms.SysConstants;
import org.sms.organization.user.entity.User;
import org.sms.organization.user.service.UserService;
import org.sms.project.encrypt.md5.MD5;
import org.sms.project.helper.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 用户登录Filter
 * @author Sunny
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  @Autowired
  private UserService userService;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    
    /**
     * 登录方法判断，仅支持POST提交
     */
    if (!SysConstants.POST_METHOE.equals(request.getMethod())) {
      throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }
    
    String username = obtainUsername(request);
    String password = obtainPassword(request);
    if (null == username || null == password || username.equals(""))
      throw new AuthenticationServiceException("用户名或者密码为空！");
    username = username.trim();
    User user = this.userService.findUserByLogin_Id(username);
    if (null == user)
      throw new AuthenticationServiceException("用户不存在！");
    if (!user.isEnabled())
      throw new AuthenticationServiceException("用户已经被锁定！");
    String salt = username + password;
    salt = MD5.encrypt(salt);
    if (!salt.equals(user.getPassword()))
      throw new AuthenticationServiceException("用户名或者密码错误！");
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, salt);
    setDetails(request, authRequest);
    SessionHelper.put(request, SysConstants.LOGIN_USER, user);
    return this.getAuthenticationManager().authenticate(authRequest);
  }
}