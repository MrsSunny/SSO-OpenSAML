package org.sms.project.blogtag.service;

import org.sms.project.blogtag.entity.BlogTag;

public interface BlogTagService {

  long insert(BlogTag blogTag);

  int update(BlogTag blogTag);

  int delete(long id);

  BlogTag findById(long id);
}
