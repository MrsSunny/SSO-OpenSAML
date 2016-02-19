package org.sms.project.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sunny
 * @since 1.8.0
 */
public class SamlFilter implements Filter {
  
  public static final String COOKIE_ANTH = "CTLPS";
  
  public static final String TICKETID = "ticket";
  
  public static final String AUTHREQUEST = "authRequest";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    Cookie[] reqCookies = req.getCookies();
    String cookieValue = null;
    
    /**
     * 从request中发现Cookie
     */
    for (Cookie reqCookie : reqCookies) {
      if (reqCookie.getName().equals(COOKIE_ANTH)) {
        cookieValue = reqCookie.getValue();
        break;
      }
    }
    /**
     * 从request中发现ticket
     */
    String ticket = req.getParameter(TICKETID);
    if (null == cookieValue) {
      res.sendRedirect("/login.jsp");
    }
    
    if (null != ticket) {
      
    }
  }

  @Override
  public void destroy() {
  }
}
