package org.sms.project.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sms.SysConstants;
import org.sms.organization.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author zhenxing.Liu
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    
    /**
     * 登录成功
     * @step1 生成登录成功身份认证断言
     * @step2 建立Artifact与身份认证断言的联系
     * @step3 跳转至返回的URL页面并带上Artifact
     */
    HttpSession session = request.getSession(false);
    
    /**
     * 获取当前登录人信息
     */
    User user = (User) session.getAttribute(SysConstants.LOGIN_USER);
    System.out.println(user);
    response.sendRedirect("/index.jsp");
  }
}