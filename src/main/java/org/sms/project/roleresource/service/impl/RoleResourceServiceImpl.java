package org.sms.project.roleresource.service.impl;

import java.util.List;
import org.sms.project.roleresource.entity.RoleResource;
import org.sms.project.roleresource.service.RoleResourceService;

/**
 * @author Sunny
 */
public class RoleResourceServiceImpl implements RoleResourceService {

  @Override
  public List<RoleResource> getRoleResource(String query, String order, int startIndex, int size) {
    return null;
  }

  @Override
  public int insert(RoleResource roleResource) {
    return 0;
  }

  @Override
  public int update(RoleResource roleResource) {
    return 0;
  }

  @Override
  public int delete(long id) {
    return 0;
  }

  @Override
  public RoleResource findById(long id) {
    return null;
  }
}