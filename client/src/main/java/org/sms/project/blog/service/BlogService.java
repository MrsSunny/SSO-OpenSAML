package org.sms.project.blog.service;

import org.sms.project.blog.entity.Blog;

public interface BlogService {

  long insert(Blog blog);

  int update(Blog blog);

  int delete(long id);

  Blog findById(long id);
}
