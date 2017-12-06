package org.sms.project.loginlog.service.impl;

import org.sms.project.loginlog.dao.LoginLogDao;
import org.sms.project.loginlog.entity.LoginLog;
import org.sms.project.loginlog.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {
  
  @Autowired
  private LoginLogDao loginLogDao;

  public long insert(LoginLog loginLog) {
    return loginLogDao.insert(loginLog);
  }

  public int update(LoginLog loginLog) {
    return loginLogDao.update(loginLog);
  }

  public int delete(long id) {
    return loginLogDao.delete(id);
  }

  public LoginLog findById(long id) {
    return loginLogDao.findById(id);
  }
}