package org.sms.project.authentication.entity;

import java.sql.Timestamp;

/**
 * @author Sunny
 */
public class SysAuthentication {

  private long id;
  
  private String sso_token;
  
  private Timestamp authentication_time;
  
  private String subject_id;
  
  private Timestamp expire_time;

  public final long getId() {
    return id;
  }

  public final void setId(long id) {
    this.id = id;
  }

  public final String getSso_token() {
    return sso_token;
  }

  public final void setSso_token(String sso_token) {
    this.sso_token = sso_token;
  }

  public final Timestamp getAuthentication_time() {
    return authentication_time;
  }

  public final void setAuthentication_time(Timestamp authentication_time) {
    this.authentication_time = authentication_time;
  }

  public final String getSubject_id() {
    return subject_id;
  }

  public final void setSubject_id(String subject_id) {
    this.subject_id = subject_id;
  }

  public final Timestamp getExpire_time() {
    return expire_time;
  }

  public final void setExpire_time(Timestamp expire_time) {
    this.expire_time = expire_time;
  }
}