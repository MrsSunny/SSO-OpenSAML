package org.sms.project.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhenxing.Liu
 */
public class SysDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
    for (ConfigAttribute allConfigAttribute : configAttributes) {
      final String needPermission = allConfigAttribute.getAttribute();
      for (GrantedAuthority ga : authentication.getAuthorities()) {
        if (needPermission.equals(ga.getAuthority())) {
          return;
        }
      }
    }
    throw new AccessDeniedException("访问受限!");
  }

  @Override
  public boolean supports(ConfigAttribute configAttribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}