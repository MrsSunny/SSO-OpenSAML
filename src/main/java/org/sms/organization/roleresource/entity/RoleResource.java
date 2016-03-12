package org.sms.organization.roleresource.entity;

import org.sms.project.base.SysBase;

/**
 * 
 * @author Sunny
 */
public class RoleResource extends SysBase {
  
  private long id;
  
  private long role_id;
  
  private long resource_id;

  public final long getId() {
    return id;
  }

  public final void setId(long id) {
    this.id = id;
  }

  public final long getRole_id() {
    return role_id;
  }

  public final void setRole_id(long role_id) {
    this.role_id = role_id;
  }

  public final long getResource_id() {
    return resource_id;
  }

  public final void setResource_id(long resource_id) {
    this.resource_id = resource_id;
  }
}