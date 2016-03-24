package org.sms.project.app.service;

import org.sms.project.app.entity.App;

/**
 * @author Sunny
 */
public interface AppService {
  
  public int insert(App app);
  
  public App findAppById(long id);
  
  public App findAppByAppName(String appName);
}