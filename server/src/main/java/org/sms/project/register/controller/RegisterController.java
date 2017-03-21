package org.sms.project.register.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "")
  public String list(Model model, @Valid User user, HttpServletRequest request) {
    // User user = new User();
    // user.setAdress("Beijing shi");
    // user.setEmail("domain@163.com");
    // user.setUsable_status("1");
    // user.setPassword("beijingshi");
    // user.setConfirmnum(0);
    // user.setImage_path("");
    // user.setLast_login_ip("192.168.1.1");
    // user.setName("Sunny");
    // user.setLogin_id(UUIDFactory.INSTANCE.getUUID());
    String email = user.getEmail();
    userService.findUserByEmail(email);
//    String password = user.getPassword();
    return "/login/login_success";
  }
}