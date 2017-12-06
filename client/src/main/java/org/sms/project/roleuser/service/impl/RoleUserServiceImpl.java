package org.sms.project.roleuser.service.impl;

import java.util.List;

import org.sms.core.id.IDFactory;
import org.sms.project.roleuser.dao.RoleUserDao;
import org.sms.project.roleuser.entity.RoleUser;
import org.sms.project.roleuser.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service("roleUserService")
public class RoleUserServiceImpl implements RoleUserService {
  
  public static final String TABLE_NAME = "SEQUENCE_ROLE_USER";
  
  @Autowired
  private RoleUserDao roleUserDao;

  @Override
  public List<RoleUser> getRoleUser(String query, String order, int startIndex, int size) {
    return null;
  }

  @Override
  public int insert(RoleUser roleUser) {
    roleUser.setId(IDFactory.INSTANCE.getId(TABLE_NAME));
    return roleUserDao.insert(roleUser);
  }

  @Override
  public int update(RoleUser roleUser) {
    return roleUserDao.update(roleUser);
  }

  @Override
  public int delete(long id) {
    return roleUserDao.delete(id);
  }

  @Override
  public RoleUser findById(long id) {
    return roleUserDao.findById(id);
  }
}