package org.sms.project.blogtag.service.impl;

import org.sms.project.blogtag.dao.BlogTagDao;
import org.sms.project.blogtag.entity.BlogTag;
import org.sms.project.blogtag.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("blogTagService")
public class BlogTagServiceImpl implements BlogTagService {

  @Autowired
  private BlogTagDao blogTagDao;

  public long insert(BlogTag blogTag) {
    return blogTagDao.insert(blogTag);
  }

  public int update(BlogTag blogTag) {
    return blogTagDao.update(blogTag);
  }

  public int delete(long id) {
    return blogTagDao.delete(id);
  }

  public BlogTag findById(long id) {
    return blogTagDao.findById(id);
  }
}