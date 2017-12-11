package org.sms.project.tag.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.SysConstants;
import org.sms.project.base.Result;
import org.sms.project.common.ResultAdd;
import org.sms.project.helper.SessionHelper;
import org.sms.project.page.Page;
import org.sms.project.tag.entity.Tag;
import org.sms.project.tag.service.TagService;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultAdd add(Model model, @Valid Tag tag, HttpServletRequest request) {
        User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
        tag.setCreateUserId(user.getId());
        long count = tagService.insert(tag);
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Tag tag, HttpServletRequest request) {
        return "login/login_success";
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return "login/login_success";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<Tag> list(Model model, HttpServletRequest request) {
        String pageNumberStr = request.getParameter("pageNumber");
        String pageSizeStr = request.getParameter("pageSize");
        if (Objects.isNull(pageNumberStr) || Objects.isNull(pageSizeStr)) {
            return null;
        }
        Integer pageNumber = Integer.parseInt(pageNumberStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        Page page = new Page(pageNumber, pageSize);
        List<Tag> tags = tagService.queryByCondition(page);
        int pageCount = tagService.getCount();
        page.setRecordCount(pageCount);
        Result<Tag> res = new Result<Tag>();
        res.setPage(page);
        res.setList(tags);
        return res;
    }

    @RequestMapping(value = "/isExit/{email}", method = RequestMethod.GET)
    public String isExit(@PathVariable("email") String email, HttpServletRequest request) {
        return "login/login_success";
    }
}