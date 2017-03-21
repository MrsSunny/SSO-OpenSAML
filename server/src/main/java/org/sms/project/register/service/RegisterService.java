package org.sms.project.register.service;

import org.sms.project.register.event.RegisterEvent;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service
public class RegisterService {

  @Autowired
  private ApplicationContext context;
  
  @Autowired
  private UserService userService;
  
  public long register(User user) {
//    long id = userService.insert(user);
    context.publishEvent(new RegisterEvent(user));
    return 88L;
  }
}