package org.sms.project.app.service;

import java.util.List;
import org.sms.project.app.entity.App;

/**
 * @author Sunny
 */
public interface AppService {
  
  List<App> getApp(String query, String order, int startIndex, int size);
  
  int insert(App app);
  
  App findAppById(long id);
  
  App findAppByAppName(String appName);
  
  int update(App roleUser);

  int delete(long id);
}