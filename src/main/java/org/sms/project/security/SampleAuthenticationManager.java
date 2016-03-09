package org.sms.project.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author Sunny
 * @since 1.8.0
 */
public class SampleAuthenticationManager implements AuthenticationManager {

  private List<String> list;

  public SampleAuthenticationManager(List<String> list) {
    this.list = list;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final List<GrantedAuthority> authoritys = new ArrayList<GrantedAuthority>();
    list.forEach(pereRoleName -> {
      authoritys.add(new SimpleGrantedAuthority(pereRoleName));
    });
    return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), authoritys);
  }
}