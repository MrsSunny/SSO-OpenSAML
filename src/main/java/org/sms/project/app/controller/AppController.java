package org.sms.project.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.project.app.entity.App;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/app")
public class AppController {

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String add(@Valid App app, HttpServletRequest request) {
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