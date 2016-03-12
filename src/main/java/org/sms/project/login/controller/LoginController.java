package org.sms.project.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 * @since 1.8.0
 */
@Controller
@RequestMapping("/loginPage")
public class LoginController {
  
  @RequestMapping(value = "")
  public String add(Model model, HttpServletRequest request) {
    return "login/login";
  }
  
  @RequestMapping(value = "/error")
  public String error(Model model, HttpServletRequest request) {
//    throw new RuntimeException("错误处理");
    String a = "sadfasdfas";
    int s = Integer.parseInt(a);
    System.out.println(s);
    return "/login/login_success";
  }
}
