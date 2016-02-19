package org.sms.project.resource.entity;

import org.sms.project.base.SysBase;

/**
 * @author zhenxing.Liu
 */
public class Resource extends SysBase {

  private long id;
  private String url;
  private String type;
  private String name;
  private long parentId;
  private String description;
  
  public final long getId() {
    return id;
  }

  public final void setId(long id) {
    this.id = id;
  }

  public final String getName() {
    return name;
  }

  public final void setName(String name) {
    this.name = name;
  }

  public final long getParentId() {
    return parentId;
  }

  public final void setParentId(long parentId) {
    this.parentId = parentId;
  }

  public final String getType() {
    return type;
  }

  public final void setType(String type) {
    this.type = type;
  }

  public final String getDescription() {
    return description;
  }

  public final void setDescription(String description) {
    this.description = description;
  }

  public final String getUrl() {
    return url;
  }

  public final void setUrl(String url) {
    this.url = url;
  }
}