package org.sms.project.tag.service;

import org.sms.project.tag.entity.Tag;

public interface TagService {

  int insert(Tag tag);

  int update(Tag tag);

  int delete(int id);

  Tag findById(int id);
}
