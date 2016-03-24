package org.sms.project.roleresource.service;

import java.util.List;
import org.sms.project.roleresource.entity.RoleResource;

/**
 * @author Sunny
 */
public interface RoleResourceService {

  List<RoleResource> getRoleResource(String query, String order, int startIndex, int size);

  int insert(RoleResource roleResource);

  int update(RoleResource roleResource);

  int delete(long id);

  RoleResource findById(long id);
}
