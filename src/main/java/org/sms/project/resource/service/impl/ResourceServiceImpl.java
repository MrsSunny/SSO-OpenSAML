package org.sms.project.resource.service.impl;

import java.util.List;

import org.sms.project.resource.dao.ResourceDao;
import org.sms.project.resource.dao.ResourceDao.ResourceMapping;
import org.sms.project.resource.entity.Resource;
import org.sms.project.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceDao resourceDao;

  public List<Resource> getResources() {
    return resourceDao.getResources();
  }
  
  public List<ResourceMapping> getResourceMappings() {
    return resourceDao.getResourceMapping();
  }

  public int insert(Resource resources) {
    return resourceDao.insert(resources);
  }

  public int update(Resource resources) {
    return resourceDao.update(resources);
  }

  public int delete(Resource resources) {
    return resourceDao.delete(resources);
  }

  public Resource findById(int id) {
    return resourceDao.findById(id);
  }
}
