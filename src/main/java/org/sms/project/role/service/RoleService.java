package org.sms.project.role.service;

import java.util.List;

import org.sms.project.role.entity.Role;

/**
 * @author Sunny
 */
public interface RoleService {

  List<Role> getRole(String query, String order, int startIndex, int size);

  int insert(Role role);

  int update(Role role);

  int delete(long id);

  Role findById(long id);
}
