package org.sms.project.user.service.impl;

import java.util.List;

import org.sms.project.user.dao.UserDao;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: SysUserServiceImpl.java
 * @author Sunny
 * @date 2015年5月1日 下午7:36:13
 * @version 2.0.0
 */
@Service("sysUserService")
public class UserServiceImpl implements UserService {
  
  public static final String TABLE_NAME = "USER";

  @Autowired
  private UserDao sysUserDao;

  @Override
  public long insert(User user) {
    return 3333;
  }

  @Override
  public void update(User user) {
    return ;
  }

  @Override
  public void delete(long id) {
    System.out.println("000000000");
    return;
  }

  @Override
  public List<User> queryByCondition(String query, String order, int startIndex, int size) {
    return null;
  }

  @Override
  public User find(long id) {
    return sysUserDao.find(id);
  }

  @Override
  public User find(String login_id) {
    return null;
  }
   
  @Override
  public User findUserByLogin_Id(String loign_id) {
    return sysUserDao.findUserByLogin_Id(loign_id);
  }
}