package org.sms.project.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * class_descriptions:
 * @author Sunny
 * @since 1.8.0
 */

public class SessionHelper {
 
  public static void put(HttpServletRequest request, String key, Object value) {
    HttpSession session = request.getSession(false);
    session.setAttribute(key, value);
  }
}
