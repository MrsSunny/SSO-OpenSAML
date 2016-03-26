package org.sms.project.base;

/**
 * @author Sunny
 */
public class SysBase {
  
  private String create_date;

  private String usable_status;

  private long create_user_id;

  private String modify_date;

  private long modify_user_id;

  public final String getUsable_status() {
    return usable_status;
  }

  public final void setUsable_status(String usable_status) {
    this.usable_status = usable_status;
  }

  public final long getCreate_user_id() {
    return create_user_id;
  }

  public final void setCreate_user_id(long create_user_id) {
    this.create_user_id = create_user_id;
  }

  public final String getModify_date() {
    return modify_date;
  }

  public final void setModify_date(String modify_date) {
    this.modify_date = modify_date;
  }

  public final long getModify_user_id() {
    return modify_user_id;
  }

  public final void setModify_user_id(long modify_user_id) {
    this.modify_user_id = modify_user_id;
  }

  public final String getCreate_date() {
    return create_date;
  }

  public final void setCreate_date(String create_date) {
    this.create_date = create_date;
  }
}