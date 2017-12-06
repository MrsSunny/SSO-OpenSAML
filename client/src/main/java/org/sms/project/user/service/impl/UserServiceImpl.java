package org.sms.project.user.service.impl;

import java.util.List;
import org.sms.project.user.dao.UserDao;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  /**
   * 这个表是获取主键ID的表，不是实体类对应的表，要注意 比如实体类的表名称是User，对应的获取ID的表名称是user_sequence，
   * 这里的定义应该定义成user_sequence
   */
  public static final String TABLE_NAME = "SEQUENCE_USER";

  @Autowired
  private UserDao sysUserDao;

  @Override
  public long insert(User user) {
    return sysUserDao.insert(user);
  }

  @Override
  public int update(User user) {
    return sysUserDao.update(user);
  }

  @Override
  public int delete(long id) {
    return sysUserDao.delete(id);
  }

  @Override
  public List<User> queryByCondition(String query, String order, int pageNumber, int pageSize) {
    if (pageNumber <= 0 || pageSize <= 0) {
      return null;
    }
    int startIndex = (pageNumber - 1) * pageSize + 1;
    return sysUserDao.queryByCondition(query, order, startIndex, pageSize);
  }

  @Override
  public List<User> queryByCondition(int pageNumber, int pageSize) {
    return queryByCondition(null, null, pageNumber, pageSize);
  }

  @Override
  public User findById(long id) {
    return sysUserDao.findById(id);
  }

  @Override
  public User findUserByEmail(String loign_id) {
    return sysUserDao.findUserByEmail(loign_id);
  }

  @Override
  public int getCount() {
    return sysUserDao.getCount();
  }
}