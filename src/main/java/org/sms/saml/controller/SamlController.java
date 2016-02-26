package org.sms.saml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sms.saml.service.SamlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sunny
 * @since 1.8.0
 */
@Controller
@RequestMapping("/saml")
public class SamlController {

  @Autowired
  private SamlService samlService;

  @RequestMapping("/checkTicket")
  public String checkTicket(HttpServletRequest request, HttpServletResponse response) {
    return "redirect:/login.jsp";
  }
}