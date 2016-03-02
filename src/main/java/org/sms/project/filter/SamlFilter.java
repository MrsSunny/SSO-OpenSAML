package org.sms.project.filter;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sms.SysConstants;

/**
 * @author Sunny
 * @since 1.8.0
 */
public class SamlFilter implements Filter {
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession(true);
    /**
     * 判断用户是否处于登录状态
     */
    if (session != null && session.getAttribute(SysConstants.LOGIN_USER) != null) {
      StringBuffer url = request.getRequestURL();
      String returnUrl = URLEncoder.encode(url.toString(), SysConstants.CHARSET);
      ((HttpServletResponse) response).sendRedirect(SysConstants.IDPSERVERADDRESS + "?ReturnUrl=" + returnUrl);
    }
  }

  @Override
  public void destroy() {
  }
}