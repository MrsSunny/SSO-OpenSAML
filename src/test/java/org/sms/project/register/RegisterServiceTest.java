package org.sms.project.register;

import org.junit.Test;
import org.sms.project.register.service.RegisterService;
import org.sms.project.user.entity.User;

import base.BaseTest;

/**
 * @author Sunny
 */
public class RegisterServiceTest extends BaseTest {

  private RegisterService registerService = (RegisterService) aCtx.getBean("registerService");
  
  @Test
  public void testRequest() {
    User user = new User();
    user.setAdress("Beijing shi");
    user.setEmail("domain@163.com");
    user.setUsable_status("1");
    user.setPassword("beijingshi");
    user.setConfirmnum(0);
    user.setImage_path("");
    user.setLast_login_ip("192.168.1.1");
    user.setName("Sunny");
    registerService.register(user);
  }
}