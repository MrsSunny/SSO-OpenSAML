package org.sms.project.blog.service.impl;

import org.sms.project.blog.dao.BlogDao;
import org.sms.project.blog.entity.Blog;
import org.sms.project.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
  
  @Autowired
  private BlogDao blogDao;

  public long insert(Blog blog) {
    return blogDao.insert(blog);
  }

  public int update(Blog blog) {
    return blogDao.update(blog);
  }

  public int delete(long id) {
    return blogDao.delete(id);
  }

  public Blog findById(long id) {
    return blogDao.findById(id);
  }
}