package org.sms.project.tag.service.impl;

import java.util.Date;

import org.junit.Test;
import org.sms.project.tag.entity.Tag;
import org.sms.project.tag.service.TagService;

import base.BaseTest;

public class TagServiceImplTest extends BaseTest {

  private TagService tagService = (TagService) aCtx.getBean("tagService");
  
  @Test
  public void testInsert() {
    Tag tag = new Tag();
    tag.setCreateDate(new Date());
    tag.setCreateUserId(2L);
    tag.setName("java");
    tag.setUsableStatus(0);
    tagService.insert(tag);
  }

  @Test
  public void testUpdate() {
    Tag tag = new Tag();
    tag.setCreateDate(new Date());
    tag.setCreateUserId(2L);
    tag.setName("javaEye");
    tagService.update(tag);
  }
  
  @Test
  public void testFindById() {
    Tag tag = tagService.findById(1);
    System.out.println(tag.getName());
  }

  @Test
  public void testDelete() {
    tagService.delete(1);
  }
}
