package org.sms.project.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.sms.SysConstants;
import org.sms.project.init.SysConfig;

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
    final String uri = request.getRequestURI();
    if (isIgnoreUrl(uri)) {
      chain.doFilter(request, response);
    } else if (session.getAttribute(SysConstants.LOGIN_USER) == null) {
      session.setAttribute(SysConstants.REDIRECT_URL_KEY, uri.toString());
      response.sendRedirect(SysConstants.SEND_ARTIFACT_URI + "?" + URLEncoder.encode(request.getRequestURL().toString(), SysConstants.CHARSET));
    } else {
      chain.doFilter(request, response);
    }
  }

  /**
   * 判断是否是忽略的URL，如果是直接放行
   * @param requestUri
   * @return
   */
  public boolean isIgnoreUrl(String requestUri) {
    List<String> ignores = SysConfig.INSTANCE.getIgnoreUrls();
    Pattern pattern;
    Matcher matcher;
    for (String regx : ignores) {
      pattern = Pattern.compile(regx);
      matcher = pattern.matcher(requestUri);
      if (matcher.matches()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void destroy() {
  }
}