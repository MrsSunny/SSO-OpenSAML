package org.sms.organization.user.controller;

import javax.servlet.http.HttpServletRequest;
import org.sms.organization.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhenxing.Liu
 */
@Controller
@RequestMapping("/sysuser")
public class UserController {

  @Autowired
  private UserService sysUserService;

  @RequestMapping(value = "/list")
  public String list(Model model, HttpServletRequest request) {
    return "/login/login_success";
  }
  
  @RequestMapping(value = "/test")
  public String test(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
}