package org.sms.project.loginlog.service.impl;

import java.util.Date;

import org.junit.Test;
import org.sms.project.loginlog.entity.LoginLog;
import org.sms.project.loginlog.service.LoginLogService;
import base.BaseTest;

public class LoginLogServiceImplTest extends BaseTest {
  
  private LoginLogService loginLogService = (LoginLogService) aCtx.getBean("loginLogService");

  @Test
  public void testInsert() {
    LoginLog loginLog = new LoginLog();
    loginLog.setIp("10.1.1.1");
    loginLog.setLoginDt("iphone 8");
    loginLog.setLoginTime(new Date());
    loginLog.setUserId(2L);
    loginLogService.insert(loginLog);
  }

  @Test
  public void testUpdate() {
    LoginLog loginLog = new LoginLog();
    loginLog.setIp("10.1.1.1");
    loginLog.setLoginDt("iphone 8");
    loginLog.setLoginTime(new Date());
    loginLog.setId(1L);
    loginLogService.update(loginLog);
  }

  @Test
  public void testDelete() {
    loginLogService.delete(1L);
  }

  @Test
  public void testFindById() {
    loginLogService.findById(1L);
  }

}
