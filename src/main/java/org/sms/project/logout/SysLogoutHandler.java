package org.sms.project.logout;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sms.SysConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author Sunny
 */
public class SysLogoutHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    final Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      String name = cookie.getName();
      if (SysConstants.IDP_TICKET.equals(name.trim())) {
        Cookie cookieTicket = new Cookie(SysConstants.IDP_TICKET, null);
        cookieTicket.setMaxAge(0);
        cookieTicket.setPath("/");
        cookieTicket.setDomain(".soaer.com");
        cookieTicket.setValue(null);
        response.addCookie(cookieTicket);
        break;
      }
    }
    response.sendRedirect("/loginPage");
  }
}