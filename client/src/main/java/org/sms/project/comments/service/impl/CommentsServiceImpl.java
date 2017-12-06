package org.sms.project.comments.service.impl;

import org.sms.project.comments.dao.CommentsDao;
import org.sms.project.comments.entity.Comments;
import org.sms.project.comments.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commentsService")
public class CommentsServiceImpl implements CommentsService {
  
  @Autowired
  private CommentsDao commentsDao;

  public long insert(Comments comments) {
    return commentsDao.insert(comments);
  }

  public int update(Comments comments) {
    return commentsDao.update(comments);
  }

  public int delete(long id) {
    return commentsDao.delete(id);
  }

  public Comments findById(long id) {
    return commentsDao.findById(id);
  }
}