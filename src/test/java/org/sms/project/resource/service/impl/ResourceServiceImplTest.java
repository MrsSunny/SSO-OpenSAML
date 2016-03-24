package org.sms.project.resource.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.sms.core.id.UUIDFactory;
import org.sms.project.resource.entity.Resource;
import org.sms.project.resource.service.ResourceService;

import base.BaseTest;

/**
 * @author Sunny
 */
public class ResourceServiceImplTest extends BaseTest {
  
  private ResourceService resourceService = (ResourceService) aCtx.getBean("resourceService");

  /**
   * Test method for {@link org.sms.project.resource.service.impl.ResourceServiceImpl#getResources()}.
   */
  @Test
  public void testGetResources() {
    List<Resource> list = resourceService.getResources(null, null, 1, 1);
    System.out.println(list.size());
  }

  /**
   * Test method for {@link org.sms.project.resource.service.impl.ResourceServiceImpl#insert(org.sms.project.resource.entity.Resource)}.
   */
  @Test
  public void testInsert() {
    Resource resource = new Resource();
    resource.setUrl("/baidu/create/" + UUIDFactory.INSTANCE.getUUID());
    resource.setCreate_user_id(67L);
    resource.setType("page");
    resource.setDescription("dfghjkl");
    resource.setName("Opnine");
    resource.setUsable_status("1");
    int i = resourceService.insert(resource);
    Assert.assertNotSame(0, i);
  }

  /**
   * Test method for {@link org.sms.project.resource.service.impl.ResourceServiceImpl#update(org.sms.project.resource.entity.Resource)}.
   */
  @Test
  public void testUpdate() {
    Resource resource = new Resource();
    resource.setUrl("/baidu/create/" + UUIDFactory.INSTANCE.getUUID());
    resource.setModify_user_id(67L);
    resource.setType("page");
    resource.setDescription("dfghjkl");
    resource.setName("Opnine");
    resource.setUsable_status("1");
    resource.setId(8L);
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    Date date = new Date();
    String str = sdf.format(date);
    resource.setModify_date(str);
    int i = resourceService.update(resource);
    Assert.assertNotSame(0, i);
  }

  /**
   * Test method for {@link org.sms.project.resource.service.impl.ResourceServiceImpl#delete(org.sms.project.resource.entity.Resource)}.
   */
  @Test
  public void testDelete() {
    int i = resourceService.delete(12L);
    Assert.assertNotSame(0, i);
  }

  /**
   * Test method for {@link org.sms.project.resource.service.impl.ResourceServiceImpl#findById(int)}.
   */
  @Test
  public void testFindById() {
    Resource resource = resourceService.findById(10L);
    Assert.assertNotNull(resource);
  }
}