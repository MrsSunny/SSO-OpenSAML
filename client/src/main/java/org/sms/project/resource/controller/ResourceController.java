package org.sms.project.resource.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.SysConstants;
import org.sms.project.base.Result;
import org.sms.project.common.ResultAdd;
import org.sms.project.helper.SessionHelper;
import org.sms.project.page.Page;
import org.sms.project.resource.entity.Resource;
import org.sms.project.resource.service.ResourceService;
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
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultAdd add(Model model, @Valid Resource resource, HttpServletRequest request) {
        User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
        resource.setCreateUserId(user.getId());
        long count = resourceService.insert(resource);
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
    public String update(@Valid Resource rsource, HttpServletRequest request) {
        return "login/login_success";
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return "login/login_success";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<Resource> list(Model model, HttpServletRequest request) {
        String pageNumberStr = request.getParameter("pageNumber");
        String pageSizeStr = request.getParameter("pageSize");
        if (Objects.isNull(pageNumberStr) || Objects.isNull(pageSizeStr)) {
            return null;
        }
        Integer pageNumber = Integer.parseInt(pageNumberStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        Page page = new Page(pageNumber, pageSize);
        List<Resource> resources = resourceService.queryByCondition(page);
        int pageCount = resourceService.getCount();
        page.setRecordCount(pageCount);
        Result<Resource> res = new Result<Resource>();
        res.setPage(page);
        res.setList(resources);
        return res;
    }

    @RequestMapping(value = "/isExit/{email}", method = RequestMethod.GET)
    public String isExit(@PathVariable("email") String email, HttpServletRequest request) {
        return "login/login_success";
    }
}