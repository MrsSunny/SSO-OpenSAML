package org.sms.project.authentication.service;

import org.sms.project.authentication.entity.SysAuthentication;

/**
 * @author Sunny
 */
public interface SysAuthenticationService {
  

  public int create(SysAuthentication sysAuthentication);
  
  public SysAuthentication queryBySSOToken(String SSOToken);
}