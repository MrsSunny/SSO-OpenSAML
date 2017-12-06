package org.sms.project.blog.service.impl;

import java.util.Date;
import org.junit.Test;
import org.sms.project.blog.entity.Blog;
import org.sms.project.blog.service.BlogService;

import base.BaseTest;
public class BlogServiceImplTest extends BaseTest {

  private BlogService blogService = (BlogService) aCtx.getBean("blogService");
  
  @Test
  public void insert() {
    Blog blog = new Blog();
    blog.setContent("jlasfasdfljasfasdf");
    blog.setCreateDate(new Date());
    blog.setCreateUserId(1L);
    blog.setMdFilePath("/user/a.md");
    blog.setReadNum(0L);
    blog.setTitle("学习Nginx编程");
    blog.setUsableStatus(0);
    blogService.insert(blog);
  }
  
  @Test
  public void update() {
    Blog blog = new Blog();
    blog.setId(1L);
    blog.setContent("jlasfasdfljasfasdf");
    blog.setCreateDate(new Date());
    blog.setCreateUserId(1L);
    blog.setMdFilePath("/user/a.md");
    blog.setReadNum(0L);
    blog.setTitle("学习Nginx编程快上手");
    blog.setUsableStatus(0);
    blogService.update(blog);
  }
  
  @Test
  public void delete() {
    blogService.delete(1L);
  }
  
  @Test
  public void findById() {
    Blog blog = blogService.findById(2L);
    System.out.println(blog.getContent());
  }
}
