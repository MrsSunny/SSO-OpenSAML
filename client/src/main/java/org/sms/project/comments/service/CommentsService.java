package org.sms.project.comments.service;

import org.sms.project.comments.entity.Comments;

public interface CommentsService {

  long insert(Comments comments);

  int update(Comments comments);

  int delete(long id);

  Comments findById(long id);
}
