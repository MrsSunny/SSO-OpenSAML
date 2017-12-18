package org.sms.project.filemanage.service;

import org.sms.project.filemanage.entity.FileManage;

public interface FileManageService {
    
    int deleteByPrimaryKey(String id);

    int insert(FileManage record);

    FileManage selectByPrimaryKey(String id);

    int updateByPrimaryKey(FileManage record);
}