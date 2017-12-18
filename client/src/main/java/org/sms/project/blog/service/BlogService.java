package org.sms.project.blog.service;

import java.util.List;

import org.sms.project.base.UploadFileBase;
import org.sms.project.blog.entity.Blog;
import org.sms.project.page.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {

    long insert(Blog blog);

    int update(Blog blog);

    int delete(long id);

    Blog findById(long id);

    int getCount();

    List<Blog> queryByCondition(Page page);

    List<Blog> queryByCondition(String query, String order, Page page);
    
    UploadFileBase addUpload(MultipartFile file);
}
