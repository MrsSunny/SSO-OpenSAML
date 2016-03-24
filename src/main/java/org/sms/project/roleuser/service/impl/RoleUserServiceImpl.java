package org.sms.project.roleuser.service.impl;

import java.util.List;

import org.sms.project.roleuser.entity.RoleUser;
import org.sms.project.roleuser.service.RoleUserService;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */

@Service("roleUserService")
public class RoleUserServiceImpl implements RoleUserService {

  @Override
  public List<RoleUser> getRoleUser(String query, String order, int startIndex, int size) {
    return null;
  }

  @Override
  public int insert(RoleUser roleUser) {
    return 0;
  }

  @Override
  public int update(RoleUser roleUser) {
    return 0;
  }

  @Override
  public int delete(long id) {
    return 0;
  }

  @Override
  public RoleUser findById(long id) {
    return null;
  }
}