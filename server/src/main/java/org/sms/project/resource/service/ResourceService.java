package org.sms.project.resource.service;

import java.util.List;

import org.sms.project.resource.entity.Resource;
import org.sms.project.resource.entity.ResourceMapping;

/**
 * @author Sunny
 */
public interface ResourceService {

  List<Resource> getResources(String query, String order, int startIndex, int size);
  
  List<ResourceMapping> getResourceMappings();
  
  int insert(Resource resources);
  
  int update(Resource resources);
  
  int delete(long id);
  
  Resource findById(long id);
}
