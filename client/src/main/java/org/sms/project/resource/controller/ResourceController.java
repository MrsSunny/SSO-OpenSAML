package org.sms.project.resource.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
  
//  @Autowired
//  private ResourceService resourceService;
  
  @RequestMapping(value = "/add")
  public String add(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
  
  @RequestMapping(value = "/update")
  public String update(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
  
  @RequestMapping(value = "/delete")
  public String delete(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
  
  @RequestMapping(value = "/search")
  public String search(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
  
  @RequestMapping(value = "/list")
  public String list(Model model, HttpServletRequest request) {
    return "login/login_success";
  }
}