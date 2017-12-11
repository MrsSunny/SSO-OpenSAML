package org.sms.project.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.SysConstants;
import org.sms.project.base.Result;
import org.sms.project.blog.entity.Blog;
import org.sms.project.blog.service.BlogService;
import org.sms.project.common.ResultAdd;
import org.sms.project.helper.SessionHelper;
import org.sms.project.page.Page;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultAdd add(Model model, @Valid Blog blog, HttpServletRequest request) {
        User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
        blog.setCreateUserId(user.getId());
        long count = blogService.insert(blog);
        ResultAdd resAdd = new ResultAdd();
        if (count == 0) {
            resAdd.setCode(0);
            resAdd.setMessage("数据格式错误");
            return resAdd;
        }
        resAdd.setCode(1);
        resAdd.setMessage("添加成功");
        return resAdd;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        InputStream in = null;
        OutputStream out = null;
        System.out.println("=================");
        try {
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "tmpFiles");
            if (!dir.exists())
                dir.mkdirs();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            System.out.println(serverFile.getPath());
            in = file.getInputStream();
            out = new FileOutputStream(serverFile);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            out.close();
            in.close();

        } catch (Exception e) {
            return null;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                out = null;
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                in = null;
            }
        }
        return "login/login_success";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Blog blog, HttpServletRequest request) {
        return "login/login_success";
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return "login/login_success";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<Blog> list(Model model, HttpServletRequest request) {
        String pageNumberStr = request.getParameter("pageNumber");
        String pageSizeStr = request.getParameter("pageSize");
        if (Objects.isNull(pageNumberStr) || Objects.isNull(pageSizeStr)) {
            return null;
        }
        Integer pageNumber = Integer.parseInt(pageNumberStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        Page page = new Page(pageNumber, pageSize);
        List<Blog> blogs = blogService.queryByCondition(page);
        int pageCount = blogService.getCount();
        page.setRecordCount(pageCount);
        Result<Blog> res = new Result<Blog>();
        res.setPage(page);
        res.setList(blogs);
        return res;
    }

    @RequestMapping(value = "/isExit/{email}", method = RequestMethod.GET)
    public String isExit(@PathVariable("email") String email, HttpServletRequest request) {
        return "login/login_success";
    }
}