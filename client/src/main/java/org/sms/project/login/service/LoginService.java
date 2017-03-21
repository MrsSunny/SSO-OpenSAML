package org.sms.project.login.service;

import javax.servlet.http.HttpSession;

/**
 * @author Sunny
 */
public interface LoginService {
  void executeDestroyUser(HttpSession session);
}