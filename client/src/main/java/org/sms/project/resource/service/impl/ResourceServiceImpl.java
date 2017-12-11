package org.sms.project.resource.service.impl;

import java.util.Date;
import java.util.List;

import org.sms.project.page.Page;
import org.sms.project.resource.dao.ResourceDao;
import org.sms.project.resource.entity.Resource;
import org.sms.project.resource.entity.ResourceMapping;
import org.sms.project.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    public static final String TABLE_NAME = "SEQUENCE_RESOURCE";

    @Autowired
    private ResourceDao resourceDao;

    public List<Resource> getResources(String query, String order, int startIndex, int size) {
        return null;
    }

    public List<ResourceMapping> getResourceMappings() {
        return null;
    }

    public int insert(Resource resource) {
        resource.setCreateDate(new Date());
        resource.setType("1");
        return resourceDao.insert(resource);
    }

    public int update(Resource resource) {
        return resourceDao.update(resource);
    }

    public int delete(long id) {
        return resourceDao.delete(id);
    }

    public Resource findById(long id) {
        return resourceDao.findById(id);
    }

    @Override
    public List<Resource> queryByCondition(String query, String order, Page page) {
        return resourceDao.queryByCondition(query, order, page);
    }

    @Override
    public List<Resource> queryByCondition(Page page) {
        return this.queryByCondition(null, null, page);
    }

    @Override
    public int getCount() {
        return resourceDao.getCount();
    }
}