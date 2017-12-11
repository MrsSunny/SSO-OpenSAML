package org.sms.project.tag.service.impl;

import java.util.Date;
import java.util.List;

import org.sms.project.page.Page;
import org.sms.project.tag.dao.TagDao;
import org.sms.project.tag.entity.Tag;
import org.sms.project.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public int insert(Tag tag) {
        tag.setCreateDate(new Date());
        return tagDao.insert(tag);
    }

    @Override
    public int update(Tag tag) {
        return tagDao.update(tag);
    }

    @Override
    public int delete(int id) {
        return tagDao.delete(id);
    }

    @Override
    public Tag findById(int id) {
        return tagDao.findById(id);
    }

    @Override
    public List<Tag> queryByCondition(String query, String order, Page page) {
        return tagDao.queryByCondition(query, order, page);
    }

    @Override
    public List<Tag> queryByCondition(Page page) {
        return this.queryByCondition(null, null, page);
    }

    @Override
    public int getCount() {
        return tagDao.getCount();
    }
}