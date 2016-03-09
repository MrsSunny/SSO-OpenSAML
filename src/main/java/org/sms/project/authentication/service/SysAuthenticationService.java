package org.sms.project.authentication.service;

import org.sms.project.authentication.dao.SysAuthenticationDao;
import org.sms.project.authentication.entity.SysAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 * @since 1.8.0
 */
@Service
public class SysAuthenticationService {
  
  @Autowired
  private SysAuthenticationDao sysAuthenticationDao;

  public int create(SysAuthentication sysAuthentication) {
    return sysAuthenticationDao.create(sysAuthentication);
  }
  
  public SysAuthentication queryById(long id) {
    return sysAuthenticationDao.queryById(id);
  }
}