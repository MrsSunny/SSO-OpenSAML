package org.sms.project.loginlog.service;

import org.sms.project.loginlog.entity.LoginLog;

public interface LoginLogService {

  long insert(LoginLog loginLog);

  int update(LoginLog loginLog);

  int delete(long id);

  LoginLog findById(long id);
}
