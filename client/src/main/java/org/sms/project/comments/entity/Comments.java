package org.sms.project.comments.entity;

import java.util.Date;

public class Comments {
  private Long id;

  private Long createUserId;

  private Long blogId;

  private Long parentCommentId;

  private String content;

  private Integer usableStatus;

  private Date createDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Long getBlogId() {
    return blogId;
  }

  public void setBlogId(Long blogId) {
    this.blogId = blogId;
  }

  public Long getParentCommentId() {
    return parentCommentId;
  }

  public void setParentCommentId(Long parentCommentId) {
    this.parentCommentId = parentCommentId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getUsableStatus() {
    return usableStatus;
  }

  public void setUsableStatus(Integer usableStatus) {
    this.usableStatus = usableStatus;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}