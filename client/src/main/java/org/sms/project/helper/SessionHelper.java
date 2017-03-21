package org.sms.project.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.sms.SysConstants;
import org.sms.project.user.entity.User;

/**
 * Session 辅助类， 用于初始化Sessoin 和获取数据等操作
 * @author Sunny
 */
public class SessionHelper {

  public static void put(HttpServletRequest request, String key, Object value) {
    HttpSession session = request.getSession(false);
    if (null != session) {
      session.setAttribute(key, value);
    }
  }
  
  public static Object get(HttpServletRequest request, String key) {
    HttpSession session = request.getSession(false);
    return null == session ? null : session.getAttribute(key);
  }
  
  /**
   * 用于Cookie登录后初始化Session
   * @param request
   * @param user
   */
  public static void init(HttpServletRequest request, User user) {
    HttpSession session = request.getSession();
    session.setAttribute(SysConstants.LOGIN_USER, user);
  }
}