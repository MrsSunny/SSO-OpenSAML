package org.sms.project.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.sms.core.id.UUIDFactory;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;

import base.BaseTest;

/**
 * @author Sunny
 */
public class UserServiceImplTest extends BaseTest {

  private UserService userService = (UserService) aCtx.getBean("userService");

  /**
   * Test method for
   * {@link org.sms.project.user.service.impl.UserServiceImpl#insert(org.sms.project.user.entity.User)}
   * .
   */
  @Test
  public void testInsert() {
    User user = new User();
    user.setAdress("Beijing shi");
    user.setEmail("domain@163.com");
    user.setUsable_status("1");
    user.setPassword("beijingshi");
    user.setConfirmnum(0);
    user.setImage_path("");
    user.setLast_login_ip("192.168.1.1");
    user.setName("Sunny");
    user.setLogin_id(UUIDFactory.INSTANCE.getUUID());
    userService.insert(user);
  }

  /**
   * Test method for
   * {@link org.sms.project.user.service.impl.UserServiceImpl#update(org.sms.project.user.entity.User)}
   * .
   */
  @Test
  public void testUpdate() {
    User user = new User();
    user.setId(48L);
    user.setAdress("Shanghai shi");
    user.setEmail("domain@hotmail.com");
    user.setImage_path("http://domain.com/persion.jpg");
    user.setLast_login_ip("192.168.1.2");
    user.setPhone("13166666666");
    user.setName("Sunny. Liu");
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    Date date = new Date();
    String str = sdf.format(date);
    user.setModify_date(str);
    int count = userService.update(user);
    Assert.assertNotSame(0, count);
  }

  /**
   * Test method for
   * {@link org.sms.project.user.service.impl.UserServiceImpl#delete(long)}.
   */
  @Test
  public void testDelete() {
    int i = userService.delete(62L);
    System.out.println(i);
    Assert.assertNotSame(0, i);
  }

  /**
   * Test method for
   * {@link org.sms.project.user.service.impl.UserServiceImpl#queryByCondition(java.lang.String, java.lang.String, int, int)}
   * .
   */
  @Test
  public void testQueryByCondition() {
    List<User> list = userService.queryByCondition(null, null, 1, 2);
    Assert.assertNotNull(list);
  }

  /**
   * Test method for
   * {@link org.sms.project.user.service.impl.UserServiceImpl#findUserByLogin_Id(java.lang.String)}
   * .
   */
  @Test
  public void testFindUserByLoginId() {
    User user = userService.findUserByLoginId("admin");
    Assert.assertNotNull(user);
  }

  @Test
  public void testFindUserById() {
    User user = userService.findById(50L);
    Assert.assertNotNull(user);
  }
}