package org.sms.saml.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sms.saml.service.CookieCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Sunny
 * @since 1.8.0
 */
@Controller
public class CookieCheckController {
  
  @Autowired
  private CookieCheckService cookieCheckService;

  public String checkCookie(HttpServletRequest request, HttpServletResponse response) {
    Cookie[] reqCookies = request.getCookies();
    String cookieValue = null;
    for (Cookie cookie: reqCookies) {
      if (cookie.getName().equals("IDP")) {
        cookieValue = cookie.getValue();
      }
    }
    if (null == cookieValue)
      return "redirect:/login.jsp";
    
    boolean isChecked = cookieCheckService.checkCookie(cookieValue);
    if (!isChecked)
      return null;
    return null;
  }
}
