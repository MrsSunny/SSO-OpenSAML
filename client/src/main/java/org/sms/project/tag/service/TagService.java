package org.sms.project.tag.service;

import java.util.List;

import org.sms.project.page.Page;
import org.sms.project.tag.entity.Tag;

public interface TagService {

  int insert(Tag tag);

  int update(Tag tag);

  int delete(int id);

  Tag findById(int id);
  
  List<Tag> queryByCondition(String query, String order, Page page);
  
  List<Tag> queryByCondition(Page page);
  
  int getCount();
}
