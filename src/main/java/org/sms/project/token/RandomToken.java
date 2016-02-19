package org.sms.project.token;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * @Title: RandomToken.java
 * @Description: 防止CSRF攻击
 * @author zhenxing.Liu
 * @date 2015年5月10日 下午3:50:40
 * @version 2.0.0
 */
public final class RandomToken {

  /**
   * token名称
   */
  private static final String SESSION_TOKEN = "sessionToken";

  /**
   * sessin中加入 token
   * @param session
   * @return
   */
  public static String getSessionToken(HttpSession session) {
    String token = null;
    synchronized (session) {
      token = (String) session.getAttribute(SESSION_TOKEN);
      if (null == token) {
        token = UUID.randomUUID().toString().replace("-", "");
        session.setAttribute(SESSION_TOKEN, token);
      }
    }
    return token;
  }

  /**
   * 从request中获取token
   * @param request
   * @return
   */
  public static String getTokenFromRequest(HttpServletRequest request) {
    return request.getParameter(SESSION_TOKEN);
  }
}