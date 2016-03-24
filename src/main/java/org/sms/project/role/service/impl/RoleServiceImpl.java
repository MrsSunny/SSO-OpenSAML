package org.sms.project.role.service.impl;

import java.util.List;

import org.sms.project.role.entity.Role;
import org.sms.project.role.service.RoleService;

/**
 * @author Sunny
 */
public class RoleServiceImpl implements RoleService {

  @Override
  public List<Role> getRole(String query, String order, int startIndex, int size) {
    return null;
  }

  @Override
  public int insert(Role sysRole) {
    return 0;
  }

  @Override
  public int update(Role sysRole) {
    return 0;
  }

  @Override
  public int delete(long id) {
    return 0;
  }

  @Override
  public Role findById(long id) {
    return null;
  }
}