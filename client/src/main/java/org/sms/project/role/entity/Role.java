package org.sms.project.role.entity;

import java.io.Serializable;

import org.sms.project.base.SysBase;

/**
 * @author Sunny
 */
public class Role extends SysBase implements Serializable {

  private static final long serialVersionUID = -1788927575820591286L;

  private long id;

  private String name;

  private String description;

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
}