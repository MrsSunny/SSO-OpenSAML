package org.sms.saml.service;

import org.springframework.stereotype.Service;

/**
 * 
 * class_descriptions:
 * @author Sunny
 * @since 1.8.0
 */

@Service
public class CookieCheckService {

  public boolean checkCookie(String cookieValue) {
    return true;
  }
  
  public boolean cookieIsOverdue() {
    return false;
  }
}
