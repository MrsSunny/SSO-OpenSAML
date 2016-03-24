package org.sms.project.roleresource.service.impl;

import java.util.List;
import org.sms.project.roleresource.dao.RoleResourceDao;
import org.sms.project.roleresource.entity.RoleResource;
import org.sms.project.roleresource.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Sunny
 */
public class RoleResourceServiceImpl implements RoleResourceService {
  
  public static final String TABLE_NAME = "role_resource_sequence";
  
  @Autowired
  private RoleResourceDao roleResourceDao;

  @Override
  public List<RoleResource> getRoleResource(String query, String order, int startIndex, int size) {
    return roleResourceDao.getRoleResource(query, order, startIndex, size);
  }

  @Override
  public int insert(RoleResource roleResource) {
    return roleResourceDao.insert(roleResource);
  }

  @Override
  public int update(RoleResource roleResource) {
    return roleResourceDao.update(roleResource);
  }

  @Override
  public int delete(long id) {
    return roleResourceDao.delete(id);
  }

  @Override
  public RoleResource findById(long id) {
    return roleResourceDao.findById(id);
  }
}