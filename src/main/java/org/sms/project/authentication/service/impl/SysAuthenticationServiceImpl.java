package org.sms.project.authentication.service.impl;

import org.sms.project.authentication.dao.SysAuthenticationDao;
import org.sms.project.authentication.entity.SysAuthentication;
import org.sms.project.authentication.service.SysAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Sunny
 */
public class SysAuthenticationServiceImpl implements SysAuthenticationService {
  
  @Autowired
  private SysAuthenticationDao sysAuthenticationDao;

  public int create(SysAuthentication sysAuthentication) {
    return sysAuthenticationDao.create(sysAuthentication);
  }
  
  public SysAuthentication queryBySSOToken(String SSOToken) {
    return sysAuthenticationDao.queryById(SSOToken);
  }
}
