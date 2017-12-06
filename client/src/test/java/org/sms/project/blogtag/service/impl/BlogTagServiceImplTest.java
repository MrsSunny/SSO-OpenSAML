package org.sms.project.blogtag.service.impl;

import static org.junit.Assert.fail;
import java.util.Date;
import org.junit.Test;
import org.sms.project.blogtag.entity.BlogTag;
import org.sms.project.blogtag.service.BlogTagService;

import base.BaseTest;

public class BlogTagServiceImplTest extends BaseTest {
  
  private BlogTagService blogTagService = (BlogTagService) aCtx.getBean("blogTagService");

  @Test
  public void testInsert() {
    BlogTag blogTag = new BlogTag();
    blogTag.setBlogId(2L);
    blogTag.setCreateDate(new Date());
    blogTag.setTagId(2);
    blogTagService.insert(blogTag);
  }

//  @Test
//  public void testUpdate() {
//    BlogTag blogTag = new BlogTag();
//    blogTag.setBlogId(2L);
//    blogTag.setId(4L);
//    blogTag.setCreateDate(new Date());
//    blogTag.setTagId(1);
//    blogTagService.update(blogTag);
//  }

  @Test
  public void testDelete() {
    blogTagService.delete(4);
  }

  @Test
  public void testFindById() {
    BlogTag blogTag = blogTagService.findById(5);
    System.out.println(blogTag.getCreateDate());
  }

}
