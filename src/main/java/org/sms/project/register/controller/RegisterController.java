package org.sms.project.register.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

  @RequestMapping(value = "")
  public String list(Model model, HttpServletRequest request) {
    return "/login/login_success";
  }
}