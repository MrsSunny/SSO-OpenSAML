package org.sms.project.filemanage.service.impl;

import org.sms.project.filemanage.dao.FileManageDao;
import org.sms.project.filemanage.entity.FileManage;
import org.sms.project.filemanage.service.FileManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileManageService")
public class FileManageServiceImpl implements FileManageService {
    
    
    @Autowired
    private FileManageDao fileManageDao;

    @Override
    public int deleteByPrimaryKey(String id) {
        return fileManageDao.delete(id);
    }

    @Override
    public int insert(FileManage fileManage) {
        return fileManageDao.insert(fileManage);
    }

    @Override
    public FileManage selectByPrimaryKey(String id) {
        return fileManageDao.findById(id);
    }

    @Override
    public int updateByPrimaryKey(FileManage fileManage) {
        return fileManageDao.update(fileManage);
    }
}