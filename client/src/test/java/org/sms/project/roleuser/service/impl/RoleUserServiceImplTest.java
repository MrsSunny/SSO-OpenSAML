package org.sms.project.roleuser.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.sms.project.roleuser.entity.RoleUser;
import org.sms.project.roleuser.service.RoleUserService;

import base.BaseTest;

/**
 * @author Sunny
 */
public class RoleUserServiceImplTest extends BaseTest {
  
  private RoleUserService roleUserService = (RoleUserService) aCtx.getBean("roleUserService");
  /**
   * Test method for {@link org.sms.project.roleuser.service.impl.RoleUserServiceImpl#getRoleUser(java.lang.String, java.lang.String, int, int)}.
   */
//  @Test
  public void testGetRoleUser() {
    roleUserService.getRoleUser(null, null, 1, 2);
  }

  /**
   * Test method for {@link org.sms.project.roleuser.service.impl.RoleUserServiceImpl#insert(org.sms.project.roleuser.entity.RoleUser)}.
   */
  @Test
  public void testInsert() {
    RoleUser roleUser = new RoleUser();
//    roleUser.setUser_id(123L);
//    roleUser.setRole_id(234L);
    int u = roleUserService.insert(roleUser);
    Assert.assertNotSame(0, u);
  }

  /**
   * Test method for {@link org.sms.project.roleuser.service.impl.RoleUserServiceImpl#update(org.sms.project.roleuser.entity.RoleUser)}.
   */
  @Test
  public void testUpdate() {
    RoleUser roleUser = new RoleUser();
    int u = roleUserService.update(roleUser);
    Assert.assertNotSame(0, u);
  }

  /**
   * Test method for {@link org.sms.project.roleuser.service.impl.RoleUserServiceImpl#delete(long)}.
   */
  @Test
  public void testDelete() {
    int u = roleUserService.delete(0L);
    Assert.assertNotSame(0, u);
  }

  /**
   * Test method for {@link org.sms.project.roleuser.service.impl.RoleUserServiceImpl#findById(long)}.
   */
  @Test
  public void testFindById() {
    RoleUser roleUser = roleUserService.findById(3L);
    Assert.assertNotNull(roleUser);
  }
}