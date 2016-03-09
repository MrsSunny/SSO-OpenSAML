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
}
