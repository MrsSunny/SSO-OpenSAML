package org.sms.project.resource.service;

import java.util.List;

import org.sms.project.resource.dao.ResourceDao.ResourceMapping;
import org.sms.project.resource.entity.Resource;

/**
 * 
 * class_descriptions:
 * @author Sunny
 */
public interface ResourceService {

  List<Resource> getResources();
  
  List<ResourceMapping> getResourceMappings();
  
  int insert(Resource resources);
  
  int update(Resource resources);
  
  int delete(Resource resources);
  
  Resource findById(int id);
}
