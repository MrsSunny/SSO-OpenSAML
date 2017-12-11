package org.sms.project.blog.controller;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.sms.project.base.Result;
import org.sms.project.blog.entity.Blog;
import org.sms.project.blog.service.BlogService;
import org.sms.project.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView add(Model model, @Valid Blog blog, HttpServletRequest request) {
//        long id = sysUserService.insert(user);
//        model.addAttribute("id", id);
//        ModelAndView mav = new ModelAndView("jsonView");
        return null;
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