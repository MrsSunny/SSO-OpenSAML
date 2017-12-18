package org.sms.project.blog.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sms.SysConstants;
import org.sms.core.id.UUIDFactory;
import org.sms.project.base.UploadFileBase;
import org.sms.project.blog.dao.BlogDao;
import org.sms.project.blog.entity.Blog;
import org.sms.project.blog.service.BlogService;
import org.sms.project.filemanage.entity.FileManage;
import org.sms.project.filemanage.service.FileManageService;
import org.sms.project.init.SysConfig;
import org.sms.project.page.Page;
import org.sms.project.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;
    
    @Autowired
    private FileManageService fileManageService;

    public long insert(Blog blog) {
        blog.setCreateDate(new Date());
        blog.setReadNum(0L);
        String htmlId = blog.getHtmlFileId();
        String path = SysConstants.FILE_ABS_PATH + File.separator + "html" + File.separator + htmlId + ".html";
        String htmlText = FileUtil.getText(path);
        String templateText = SysConfig.INSTANCE.getCacheDate(SysConstants.BLOH_HTML_KEY);
        Document docBlog = Jsoup.parse(templateText);
        Document docSource = Jsoup.parse(htmlText);
        Elements sourceRealBody = docSource.getElementsByTag("body");
        String sourceRealBodyStr = sourceRealBody.html().toString();
        Element singerListDiv = docBlog.getElementsByAttributeValue("class", "noteAd").first();
        singerListDiv.after(sourceRealBodyStr);
        blog.setContent(docBlog.toString());
        if (blog.getUsableStatus() == 0) {
            String blogPath = SysConstants.FILE_ABS_PATH + File.separator + "blog" + File.separator + htmlId + ".html";
            FileUtil.writeText(blogPath, docBlog.toString());
        }
        return blogDao.insert(blog);
    }

    public int update(Blog blog) {
        return blogDao.update(blog);
    }

    public int delete(long id) {
        return blogDao.delete(id);
    }

    public Blog findById(long id) {
        return blogDao.findById(id);
    }

    @Override
    public List<Blog> queryByCondition(String query, String order, Page page) {
        return blogDao.queryByCondition(query, order, page);
    }

    @Override
    public List<Blog> queryByCondition(Page page) {
        return this.queryByCondition(null, null, page);
    }

    @Override
    public int getCount() {
        return blogDao.getCount();
    }

    @Override
    public UploadFileBase addUpload(MultipartFile file) {
        InputStream in = null;
        OutputStream out = null;
        UploadFileBase resAdd = new UploadFileBase();
        String sourceFileName = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        String id = UUIDFactory.INSTANCE.getUUID();
        try {
            File dir = new File(SysConstants.FILE_ABS_PATH + File.separator + suffix);
            if (!dir.exists())
                dir.mkdirs();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + id + "." + suffix);
            in = file.getInputStream();
            out = new FileOutputStream(serverFile);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            out.close();
            in.close();
            
            FileManage fileManage = new FileManage();
            fileManage.setCreateDate(new Date());
            fileManage.setFilePath(serverFile.getPath());
            fileManage.setId(id);
            fileManage.setFileType(suffix);
            int count = fileManageService.insert(fileManage);
            if (count == 0) {
                resAdd.setCode(0);
                resAdd.setError("服务器文件错误");
                return resAdd;
            }
            resAdd.setCode(1);
            resAdd.setUrl("http://soaer.com/" + id + "." + suffix);
            resAdd.setName(sourceFileName);
            resAdd.setError("添加成功");
            resAdd.setId(id);
            resAdd.setType(suffix.toLowerCase().trim());
            List<UploadFileBase> res = new ArrayList<UploadFileBase>();
            res.add(resAdd);
            return resAdd;
        } catch (Exception e) {
            e.printStackTrace();
            resAdd.setCode(0);
            resAdd.setError("服务器文件错误");
            return resAdd;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
    }
}