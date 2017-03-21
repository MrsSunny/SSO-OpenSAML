package org.sms.project.login.service.impl;

import javax.servlet.http.HttpSession;
import org.sms.project.login.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

  @Override
  public void executeDestroyUser(HttpSession session) {
  }
}