package org.sms.project.tag.service.impl;

import org.sms.project.tag.dao.TagDao;
import org.sms.project.tag.entity.Tag;
import org.sms.project.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service("tagService")
public class TagServiceImpl implements TagService {

  @Autowired
  private TagDao tagDao;

  @Override
  public int insert(Tag tag) {
    return tagDao.insert(tag);
  }

  @Override
  public int update(Tag tag) {
    return tagDao.update(tag);
  }

  @Override
  public int delete(int id) {
    return tagDao.delete(id);
  }

  @Override
  public Tag findById(int id) {
    return tagDao.findById(id);
  }
}