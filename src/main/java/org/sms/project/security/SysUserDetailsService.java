package org.sms.project.security;

import java.util.HashSet;
import java.util.Set;

import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Sunny
 */
public class SysUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService sysUserService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = sysUserService.findUserByEmail(email);
    this.buildAuths(user);
    return user;
  }

  private void buildAuths(User user) {
    Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
    auths.add(new SimpleGrantedAuthority("ADMIN"));
    user.setAuthorities(auths);
  }
}