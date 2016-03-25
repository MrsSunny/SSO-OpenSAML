package org.sms.project.resource.service.impl;

import java.util.List;
import org.sms.core.id.IDFactory;
import org.sms.project.resource.dao.ResourceDao;
import org.sms.project.resource.dao.ResourceDao.ResourceMapping;
import org.sms.project.resource.entity.Resource;
import org.sms.project.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
  
  public static final String TABLE_NAME = "SEQUENCE_RESOURCE";

  @Autowired
  private ResourceDao resourceDao;

  public List<Resource> getResources(String query, String order, int startIndex, int size) {
    return resourceDao.getResources(query, order, startIndex, size);
  }
  
  public List<ResourceMapping> getResourceMappings() {
    return resourceDao.getResourceMapping();
  }

  public int insert(Resource resources) {
    long id = IDFactory.INSTANCE.getId(TABLE_NAME);
    resources.setId(id);
    return resourceDao.insert(resources);
  }

  public int update(Resource resources) {
    return resourceDao.update(resources);
  }

  public int delete(long id) {
    return resourceDao.delete(id);
  }

  public Resource findById(long id) {
    return resourceDao.findById(id);
  }
}