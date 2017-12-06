package org.sms.project.comments.service.impl;

import java.util.Date;

import org.junit.Test;
import org.sms.project.comments.entity.Comments;
import org.sms.project.comments.service.CommentsService;

import base.BaseTest;

public class CommentsServiceImplTest extends BaseTest {
  
  private CommentsService commentsService = (CommentsService) aCtx.getBean("commentsService");

  @Test
  public void testInsert() {
    Comments comments = new Comments();
    comments.setBlogId(3L);
    comments.setContent("很不错的文章");
    comments.setCreateDate(new Date());
    comments.setCreateUserId(1L);
    comments.setUsableStatus(0);
    comments.setParentCommentId(0L);
    commentsService.insert(comments);
  }

  @Test
  public void testUpdate() {
    Comments comments = new Comments();
    comments.setBlogId(3L);
    comments.setContent("很不错的文章+1");
    comments.setCreateDate(new Date());
    comments.setCreateUserId(1L);
    comments.setUsableStatus(0);
    comments.setParentCommentId(0L);
    comments.setId(3L);
    commentsService.update(comments);
  }

  @Test
  public void testDelete() {
    commentsService.delete(1L);
  }

  @Test
  public void testFindById() {
    Comments comments = commentsService.findById(1L);
  }
}