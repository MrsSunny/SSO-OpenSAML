package org.sms.project.role.entity;

import java.util.Date;

public class Role {
  private Long id;

  private String name;

  private String description;

  private Integer usableStatus;

  private Long createUserId;

  private Date createDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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