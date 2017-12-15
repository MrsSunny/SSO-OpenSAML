package org.sms.project.user.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sms.project.base.Result;
import org.sms.project.common.ResultAdd;
import org.sms.project.page.Page;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.sms.project.util.HttpUtil;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService sysUserService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultAdd add(Model model, @Valid User user, HttpServletRequest request) {
        String ip = HttpUtil.getIp(request);
        user.setLastLoginIp(ip);
        long id = sysUserService.insert(user);
        ResultAdd resAdd = new ResultAdd();
        if (id == 0) {
            resAdd.setCode(0);
            resAdd.setError("数据格式错误");
            return resAdd;
        }
        resAdd.setCode(1);
        resAdd.setError("添加成功");
        return resAdd;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid User user, HttpServletRequest request) {
        return "login/login_success";
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") Long id, HttpServletRequest request) {
        return "login/login_success";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<User> list(Model model, HttpServletRequest request) {
        String pageNumberStr = request.getParameter("pageNumber");
        String pageSizeStr = request.getParameter("pageSize");
        if (Objects.isNull(pageNumberStr) || Objects.isNull(pageSizeStr)) {
            return null;
        }
        Integer pageNumber = Integer.parseInt(pageNumberStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        Page page = new Page(pageNumber, pageSize);
        List<User> users = sysUserService.queryByCondition(page);
        int pageCount = sysUserService.getCount();
        page.setRecordCount(pageCount);
        Result<User> res = new Result<User>();
        res.setPage(page);
        res.setList(users);
        return res;
    }

    @RequestMapping(value = "/isExit/{email}", method = RequestMethod.GET)
    public String isExit(@PathVariable("email") String email, HttpServletRequest request) {
        return "login/login_success";
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultAdd delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        int count = sysUserService.delete(Long.parseLong(id));
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