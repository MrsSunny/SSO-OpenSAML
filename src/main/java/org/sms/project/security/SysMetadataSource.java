package org.sms.project.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sms.project.resource.entity.ResourceMapping;
import org.sms.project.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

/**
 * 资源角色管理器
 * @author Sunny
 */
@Service("sysMetadataSource")
public class SysMetadataSource implements FilterInvocationSecurityMetadataSource {

  @Autowired
  private ResourceService resourceService;

  private Map<String, List<ConfigAttribute>> configAttributes;

  private RequestMatcher requestMatcher = null;

  public SysMetadataSource() {
    super();
  }

  public final ResourceService getResourceService() {
    return resourceService;
  }

  public final void setResourceService(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

    final HttpServletRequest request = ((FilterInvocation) object).getRequest();
    if (null == configAttributes)
      this.load();
    final List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
    configAttributes.forEach((K, V) -> {
      requestMatcher = new AntPathRequestMatcher(K + "/**");
      if (null != K) {
        if (requestMatcher.matches(request)) {
          if (null != V) {
            list.addAll(V);
          }
        }
      }
    });
    return list;
  }

  /**
   * 加载所有的url访问时需要的角色
   */
  private void load() {
    List<ResourceMapping> resourceMappings = resourceService.getResourceMappings();
    if (resourceMappings.size() == 0)
      return;
    this.configAttributes = new HashMap<String, List<ConfigAttribute>>();
    final List<ConfigAttribute> list = new ArrayList<ConfigAttribute>(1);
    resourceMappings.forEach(resourceMapping -> {
      final String url = resourceMapping.getUrl();
      final String role_name = resourceMapping.getName();
      if (null != url && role_name != null) {
        ConfigAttribute configAttribute = new SecurityConfig(role_name);
        list.add(configAttribute);
        final List<ConfigAttribute> isExit = configAttributes.get(url);
        if (isExit == null) {
          configAttributes.put(url, list);
        } else {
          if (isExit.contains(configAttribute)) {
            configAttributes.put(url, list);
          } else {
            isExit.addAll(list);
            configAttributes.put(url, isExit);
          }
        }
      }
    });
  }
  
  public void addResourceToConfigAttributes(String url, String ...role_names) {
    if (null == url || null == role_names || role_names.length == 0)
      return;
    ConfigAttribute configAttribute = null;
    List<ConfigAttribute> list = new ArrayList<ConfigAttribute>(role_names.length);
    if (configAttributes.get(url) == null) {
      for (String role_name : role_names) {
        configAttribute = new SecurityConfig(role_name);
        list.add(configAttribute);
      }
      configAttributes.put(url, list);
    } else {
      final List<ConfigAttribute> isExit = configAttributes.get(url);
      for (String role_name : role_names) {
        configAttribute = new SecurityConfig(role_name);
        if (!isExit.contains(configAttribute)) {
          isExit.add(configAttribute);
        }
      }
      configAttributes.put(url, isExit);
    }
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }
}