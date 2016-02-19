package org.sms.project.login.service;

import javax.servlet.http.HttpSession;

/**
 * @author zhenxing.Liu
 */
public interface LoginService {
  void executeDestroyUser(HttpSession session);
}