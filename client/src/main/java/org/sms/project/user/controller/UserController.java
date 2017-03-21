package org.sms.project.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService sysUserService;

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView add(Model model, @Valid User user, HttpServletRequest request) {
    long id = sysUserService.insert(user);
    model.addAttribute("id", id);
    ModelAndView mav = new ModelAndView("jsonView");
    return mav;
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String update(@Valid User user, HttpServletRequest request) {
    return "login/login_success";
  }
  
  @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
  public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
    return "login/login_success";
  }

  @RequestMapping(value = "/list", method = RequestMethod.POST)
  public String list(Model model, HttpServletRequest request) {
    return "/login/login_success";
  }

  @RequestMapping(value = "/isExit/{email}", method = RequestMethod.GET)
  public String isExit(@PathVariable("email") String email, HttpServletRequest request) {
    return "login/login_success";
  }
}