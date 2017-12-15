package org.sms.project.role.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.SysConstants;
import org.sms.project.base.Result;
import org.sms.project.common.ResultAdd;
import org.sms.project.helper.SessionHelper;
import org.sms.project.page.Page;
import org.sms.project.role.entity.Role;
import org.sms.project.role.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultAdd add(Model model, @Valid Role role, HttpServletRequest request) {
        User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
        role.setCreateUserId(user.getId());
        long count = roleService.insert(role);
        ResultAdd resAdd = new ResultAdd();
        if (count == 0) {
            resAdd.setCode(0);
            resAdd.setError("数据格式错误");
            return resAdd;
        }
        resAdd.setCode(1);
        resAdd.setError("添加成功");
        return resAdd;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Role role, HttpServletRequest request) {
        return "login/login_success";
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return "login/login_success";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<Role> list(Model model, HttpServletRequest request) {
        String pageNumberStr = request.getParameter("pageNumber");
        String pageSizeStr = request.getParameter("pageSize");
        if (Objects.isNull(pageNumberStr) || Objects.isNull(pageSizeStr)) {
            return null;
        }
        Integer pageNumber = Integer.parseInt(pageNumberStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        Page page = new Page(pageNumber, pageSize);
        List<Role> roles = roleService.queryByCondition(page);
        int pageCount = roleService.getCount();
        page.setRecordCount(pageCount);
        Result<Role> res = new Result<Role>();
        res.setPage(page);
        res.setList(roles);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultAdd delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        int count = roleService.delete(Long.parseLong(id));
        ResultAdd resAdd = new ResultAdd();
        if (count == 0) {
            resAdd.setCode(0);
            resAdd.setError("删除失败");
            return resAdd;
        }
        resAdd.setCode(1);
        resAdd.setError("删除成功");
        return resAdd;
    }
}