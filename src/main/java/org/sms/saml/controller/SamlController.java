package org.sms.saml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.core.AuthnRequest;
import org.sms.project.filter.SamlFilter;
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

    String authnRequest = request.getParameter(SamlFilter.AUTHREQUEST);

    /**
     * 获取AuthnRequest 对象
     */
    AuthnRequest authnRequestBean = samlService.getAuthRequest(authnRequest);
    if (null == authnRequestBean)
      return null;
    /**
     * 获取服务器的URL
     */
    String serverUrl = authnRequestBean.getAssertionConsumerServiceURL();
    if (serverUrl == null)
      return null;
    /**
     * 校验URL是否是合法的URL
     */
    boolean checkServerUrl = samlService.checkUrl(serverUrl);
    if (!checkServerUrl)
      return null;
    return "redirect:/login.jsp";
  }
  
  @RequestMapping("/checkAuthRequest")
  public String checkAuthRequest(HttpServletRequest request, HttpServletResponse response) {

    String authnRequest = request.getParameter(SamlFilter.AUTHREQUEST);

    /**
     * 获取AuthnRequest 对象
     */
    AuthnRequest authnRequestBean = samlService.getAuthRequest(authnRequest);
    if (null == authnRequestBean)
      /**
       * 如果过得不到AuthnRequest对象
       */
      return null;
    /**
     * 获取服务器的URL
     */
    String serverUrl = authnRequestBean.getAssertionConsumerServiceURL();
    if (serverUrl == null)
      return null;
    /**
     * 校验URL是否是合法的URL
     */
    boolean checkServerUrl = samlService.checkUrl(serverUrl);
    if (!checkServerUrl)
      return null;
    return "redirect:/login.jsp";
  }
}