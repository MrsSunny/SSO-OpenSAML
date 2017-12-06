package org.sms.project.tag.entity;

import java.util.Date;

public class Tag {
  private Integer id;

  private String name;

  private Integer usableStatus;

  private Long createUserId;

  private Date createDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}