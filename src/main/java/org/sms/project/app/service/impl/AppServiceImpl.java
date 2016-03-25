package org.sms.project.app.service.impl;

import java.util.List;

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
  
  public static final String TABLE_NAME = "SEQUENCE_SYS_APP";

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

  @Override
  public List<App> getApp(String query, String order, int startIndex, int size) {
    return appDao.getApp(query, order, startIndex, size);
  }

  @Override
  public int update(App roleUser) {
    return appDao.update(roleUser);
  }

  @Override
  public int delete(long id) {
    return appDao.delete(id);
  }
}