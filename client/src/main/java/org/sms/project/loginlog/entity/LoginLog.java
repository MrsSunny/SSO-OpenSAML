package org.sms.project.loginlog.entity;

import java.util.Date;

public class LoginLog {
  private Long id;

  private String ip;

  private Date loginTime;

  private String loginDt;
  
  private Long userId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public String getLoginDt() {
    return loginDt;
  }

  public void setLoginDt(String loginDt) {
    this.loginDt = loginDt;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}