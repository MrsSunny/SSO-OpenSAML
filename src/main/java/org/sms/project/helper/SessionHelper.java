package org.sms.project.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Sunny
 * @since 1.8.0
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
}