package org.sms.project.user.service;

import java.util.List;
import org.sms.project.user.entity.User;

/**
 * @author Sunny
 */
public interface UserService {
  
  long insert(User user);

  int update(User user);

  int delete(long id);

  User findById(long id);

  List<User> queryByCondition(String query, String order, int startIndex, int size);

  User findUserByLoginId(String loign_id);
}