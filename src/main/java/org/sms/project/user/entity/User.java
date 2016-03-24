package org.sms.project.user.entity;

import java.io.Serializable;
import java.util.Collection;
import org.sms.SysConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Sunny
 */
public class User implements Serializable, UserDetails {

  private static final long serialVersionUID = 4803461735816229793L;

  private Long id;

  private String name;
  
  private String image_path;

  private String login_id;

  private String password;

  private String email;

  private String phone;

  private String adress;

  private Integer confirmnum;

  private Long login_sum;

  private String last_login_ip;

  private String create_date;

  private String usable_status;

  private String modify_date;

  private String last_login_date;
  
  /**
   * 登录凭证，如果是系统内部用户则为密码,如果为其他第三方用户则存凭证
   */
  private String token;
  
  /**
   * 登录类型 QQ 登录  微信登录  百度账号登录等等
   */
  private String login_type;

  private Collection<? extends GrantedAuthority> authorities;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the user_name
   */
  public String getName() {
    return name;
  }

  /**
   * @param user_name
   *          the user_name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the login_id
   */
  public String getLogin_id() {
    return login_id;
  }

  /**
   * @param login_id
   *          the login_id to set
   */
  public void setLogin_id(String login_id) {
    this.login_id = login_id;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone
   *          the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the adress
   */
  public String getAdress() {
    return adress;
  }

  /**
   * @param adress
   *          the adress to set
   */
  public void setAdress(String adress) {
    this.adress = adress;
  }

  /**
   * @return the confirmnum
   */
  public int getConfirmnum() {
    return confirmnum;
  }

  /**
   * @param confirmnum
   *          the confirmnum to set
   */
  public void setConfirmnum(int confirmnum) {
    this.confirmnum = confirmnum;
  }

  /**
   * @return the login_sum
   */
  public Long getLogin_sum() {
    return login_sum;
  }

  /**
   * @param login_sum
   *          the login_sum to set
   */
  public void setLogin_sum(Long login_sum) {
    this.login_sum = login_sum;
  }

  /**
   * @return the last_login_ip
   */
  public String getLast_login_ip() {
    return last_login_ip;
  }

  /**
   * @param last_login_ip
   *          the last_login_ip to set
   */
  public void setLast_login_ip(String last_login_ip) {
    this.last_login_ip = last_login_ip;
  }

  /**
   * @return the last_login_date
   */
  public String getLast_login_date() {
    return last_login_date;
  }

  /**
   * @param last_login_date
   *          the last_login_date to set
   */
  public void setLast_login_date(String last_login_date) {
    this.last_login_date = last_login_date;
  }

  /**
   * @return the create_date
   */
  public String getCreate_date() {
    return create_date;
  }

  /**
   * @param create_date
   *          the create_date to set
   */
  public void setCreate_date(String create_date) {
    this.create_date = create_date;
  }

  /**
   * @return the usable_status
   */
  public String getUsable_status() {
    return usable_status;
  }

  /**
   * @param usable_status
   *          the usable_status to set
   */
  public void setUsable_status(String usable_status) {
    this.usable_status = usable_status;
  }

  /**
   * @return the modify_date
   */
  public String getModify_date() {
    return modify_date;
  }

  /**
   * @param modify_date
   *          the modify_date to set
   */
  public void setModify_date(String modify_date) {
    this.modify_date = modify_date;
  }

  public final void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public String toString() {
    return "[{" + "login_id:" + login_id + "}]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((login_id == null) ? 0 : login_id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (login_id == null) {
      if (other.login_id != null)
        return false;
    } else if (!login_id.equals(other.login_id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  public final String getToken() {
    return token;
  }

  public final void setToken(String token) {
    this.token = token;
  }

  public final String getLogin_type() {
    return login_type;
  }

  public final void setLogin_type(String login_type) {
    this.login_type = login_type;
  }
  
  public final String getImage_path() {
    return image_path;
  }

  public final void setImage_path(String image_path) {
    this.image_path = image_path;
  }

  public User(String login_id, String password, String usable_status, Collection<? extends GrantedAuthority> authorities) {
    this(login_id, password, usable_status);
    this.authorities = authorities;
  }

  public User(String login_id, String password, String usable_status) {
    this.login_id = login_id;
    this.password = password;
    this.usable_status = usable_status;
  }
  
  public User() {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return login_id;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return SysConstants.ENABLE.equals(this.usable_status);
  }
}