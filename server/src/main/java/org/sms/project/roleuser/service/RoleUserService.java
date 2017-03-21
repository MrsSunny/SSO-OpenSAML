package org.sms.project.roleuser.service;

import java.util.List;
import org.sms.project.roleuser.entity.RoleUser;

/**
 * @author Sunny
 */
public interface RoleUserService {

  List<RoleUser> getRoleUser(String query, String order, int startIndex, int size);

  int insert(RoleUser roleUser);

  int update(RoleUser roleUser);

  int delete(long id);

  RoleUser findById(long id);
}
