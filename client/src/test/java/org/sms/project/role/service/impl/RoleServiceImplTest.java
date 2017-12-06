package org.sms.project.role.service.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.sms.core.id.UUIDFactory;
import org.sms.project.role.entity.Role;
import org.sms.project.role.service.RoleService;

import base.BaseTest;

/**
 * @author Sunny
 */
public class RoleServiceImplTest extends BaseTest {
  
  private RoleService roleService = (RoleService) aCtx.getBean("roleService");

  /**
   * Test method for {@link org.sms.project.role.service.impl.RoleServiceImpl#getRole(java.lang.String, java.lang.String, int, int)}.
   */
//  @Test
//  public void testGetRole() {
//    roleService.getRole(null, null, 1, 2);
//  }

  /**
   * Test method for {@link org.sms.project.role.service.impl.RoleServiceImpl#insert(org.sms.project.role.entity.Role)}.
   */
  @Test
  public void testInsert() {
    Role role = new Role();
    role.setDescription("asdfasdf");
    role.setName(UUIDFactory.INSTANCE.getUUID());
    role.setCreateDate(new Date());
    role.setCreateUserId(2L);
    role.setUsableStatus(0);
    roleService.insert(role);
  }

  /**
   * Test method for {@link org.sms.project.role.service.impl.RoleServiceImpl#update(org.sms.project.role.entity.Role)}.
   */
  @Test
  public void testUpdate() {
    Role role = new Role();
    role.setId(1L);
    role.setDescription("wefghjhkj");
    role.setName(UUIDFactory.INSTANCE.getUUID());
    int u = roleService.update(role);
    Assert.assertNotSame(0, u);
  }

  /**
   * Test method for {@link org.sms.project.role.service.impl.RoleServiceImpl#delete(long)}.
   */
  @Test
  public void testDelete() {
    int o = roleService.delete(1L);
    Assert.assertNotSame(0, o);
  }

  /**
   * Test method for {@link org.sms.project.role.service.impl.RoleServiceImpl#findById(long)}.
   */
  @Test
  public void testFindById() {
    Role role = roleService.findById(2L);
    Assert.assertNotNull(role);
  }
}