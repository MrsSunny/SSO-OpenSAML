package org.sms.project.blog.entity;

import java.util.Date;

public class Blog {
  private Long id;

  private String title;

  private String htmlFilePath;

  private String mdFilePath;

  private Long readNum;

  private Integer usableStatus;

  private Long createUserId;

  private Date createDate;

  private String content;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHtmlFilePath() {
    return htmlFilePath;
  }

  public void setHtmlFilePath(String htmlFilePath) {
    this.htmlFilePath = htmlFilePath;
  }

  public String getMdFilePath() {
    return mdFilePath;
  }

  public void setMdFilePath(String mdFilePath) {
    this.mdFilePath = mdFilePath;
  }

  public Long getReadNum() {
    return readNum;
  }

  public void setReadNum(Long readNum) {
    this.readNum = readNum;
  }

  public Integer getUsableStatus() {
    return usableStatus;
  }

  public void setUsableStatus(Integer usableStatus) {
    this.usableStatus = usableStatus;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}