package org.sms.project.role.controller;

import javax.servlet.http.HttpServletRequest;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private UserService sysUserService;

  @RequestMapping(value = "/add")
  public String add(Model model, HttpServletRequest request) {
    return "/login/login_success";
  }

  @RequestMapping(value = "/update")
  public String update(Model model, HttpServletRequest request) {
    return "login/login_success";
  }

  @RequestMapping(value = "/list")
  public String list(Model model, HttpServletRequest request) {
    return "/login/login_success";
  }

  @RequestMapping(value = "/isExit")
  public String isExit(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
}