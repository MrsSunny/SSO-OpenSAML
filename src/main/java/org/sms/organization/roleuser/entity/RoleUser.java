package org.sms.organization.roleuser.entity;

import org.sms.project.base.SysBase;

/**
 * @author zhenxing.Liu
 */
public class RoleUser extends SysBase {

  private long id;
  
  private long user_id;
  
  private long role_id;

  public final long getId() {
    return id;
  }

  public final void setId(long id) {
    this.id = id;
  }

  public final long getUser_id() {
    return user_id;
  }

  public final void setUser_id(long user_id) {
    this.user_id = user_id;
  }

  public final long getRole_id() {
    return role_id;
  }

  public final void setRole_id(long role_id) {
    this.role_id = role_id;
  }
}