package org.sms.project.app.service.impl;

import org.sms.project.app.dao.AppDao;
import org.sms.project.app.entity.App;
import org.sms.project.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service
public class AppServiceImpl implements AppService {

  @Autowired
  private AppDao appDao;
  
  public int insert(App app) {
    return appDao.insert(app);
  }
  
  public App findAppById(long id) {
    return appDao.findAppById(id);
  }
  
  public App findAppByAppName(String appName) {
    return appDao.findAppByAppName(appName);
  }
}