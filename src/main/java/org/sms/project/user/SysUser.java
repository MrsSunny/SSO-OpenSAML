package org.sms.project.user;

import java.sql.Timestamp;

/**
 * @Title: ISysUser.java
 * @Description: TODO
 * @author <a href="https://github.com/MrsSunny">Sunny</a>
 * @date 2015年4月11日 下午4:37:05
 * @version 2.0.0
 */
public interface SysUser {

  Long getId();

  String getUser_name();

  String getLogin_id();

  String getPassword();

  String getEmail();

  String getPhone();

  String getAdress();

  int getConfirmnum();

  String getEmployee_id();

  String getIslock();

  Long getLogin_sum();

  Timestamp getCreate_date();

  String getUsable_status();

  Timestamp getModify_date();

  String getModify_ip();

  Long getModify_user_id();
}
