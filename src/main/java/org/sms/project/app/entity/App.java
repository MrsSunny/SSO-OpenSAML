package org.sms.project.app.entity;

import org.sms.project.base.SysBase;

/**
 * @author Sunny
 */
public class App extends SysBase {
  
  private long id;
  
  private String app_domain;
  
  private String app_index;

  public final long getId() {
    return id;
  }

  public final void setId(long id) {
    this.id = id;
  }

  public final String getApp_domain() {
    return app_domain;
  }

  public final void setApp_domain(String app_domain) {
    this.app_domain = app_domain;
  }

  public final String getApp_index() {
    return app_index;
  }

  public final void setApp_index(String app_index) {
    this.app_index = app_index;
  }
}