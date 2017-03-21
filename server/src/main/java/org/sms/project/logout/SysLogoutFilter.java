package org.sms.project.logout;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author Sunny
 */
public class SysLogoutFilter extends LogoutFilter {

  /**
   * create instance by constructor SysLogoutFilter
   * @param logoutSuccessHandler
   * @param handlers
   */
  public SysLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler[] handlers) {
    super(logoutSuccessHandler, handlers);
  }
}
