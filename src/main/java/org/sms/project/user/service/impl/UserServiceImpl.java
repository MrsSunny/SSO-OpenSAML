package org.sms.project.user.service.impl;

import java.util.List;
import org.sms.core.id.IDFactory;
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
   * 这个表是获取主键ID的表，不是实体类对应的表，要注意
   * 比如实体类的表名称是User，对应的获取ID的表名称是user_sequence，
   * 这里的定义应该定义成user_sequence
   */
  public static final String TABLE_NAME = "SEQUENCE_USER";

  @Autowired
  private UserDao sysUserDao;

  @Override
  public long insert(User user) {
    long id = IDFactory.INSTANCE.getId(TABLE_NAME);
    user.setId(id);
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
  public List<User> queryByCondition(String query, String order, int startIndex, int size) {
   return sysUserDao.queryByCondition(query, order, startIndex, size);
  }

  @Override
  public User findById(long id) {
    return sysUserDao.findById(id);
  }

  @Override
  public User findUserByLoginId(String loign_id) {
    return sysUserDao.findUserByLoginId(loign_id);
  }
}