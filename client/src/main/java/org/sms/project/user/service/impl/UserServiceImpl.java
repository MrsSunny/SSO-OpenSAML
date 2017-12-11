package org.sms.project.user.service.impl;

import java.util.Date;
import java.util.List;
import org.sms.project.page.Page;
import org.sms.project.user.dao.UserDao;
import org.sms.project.user.entity.User;
import org.sms.project.user.service.UserService;
import org.sms.project.util.AccountValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sunny
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao sysUserDao;

    @Override
    public long insert(User user) {
        boolean isCorrect = this.checkUser(user);
        if (!isCorrect) {
            return 0;
        }
        user.setLoginSum(0);
        user.setLastLoginDate(new Date());
        user.setCreateDate(new Date());
        user.setLoginType(0);
        int count = this.findUserByEmailAndName(user.getEmail(), user.getName());
        if (count >= 1) {
            return 0;
        }
        long successInsert = sysUserDao.insert(user);
        return successInsert;
    }

    public int findUserByEmailAndName(String email, String name) {
        return sysUserDao.findUserByEmailAndName(email, name);
    }

    @Override
    public int update(User user) {
        return sysUserDao.update(user);
    }

    @Override
    public int delete(long id) {
        return sysUserDao.delete(id);
    }

    @Override
    public List<User> queryByCondition(String query, String order, Page page) {
        return sysUserDao.queryByCondition(query, order, page);
    }

    @Override
    public List<User> queryByCondition(Page page) {
        return queryByCondition(null, null, page);
    }

    @Override
    public User findById(long id) {
        return sysUserDao.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return sysUserDao.findUserByEmail(email);
    }

    @Override
    public int getCount() {
        return sysUserDao.getCount();
    }
    
    private boolean checkUser(User user) {
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        if (name == null || email == null || password == null || name.trim() == "" || email.trim() == "" || password.trim() == "") {
            return false;
        }
        if (name.length() >= 15 || password.length() < 6 || password.length() > 18) {
            return false;
        }
        
        if (!AccountValidatorUtil.isUsername(name) || !AccountValidatorUtil.isEmail(email) || !AccountValidatorUtil.isPassword(password)) {
            return false;
        }
        if (user.getAddress() != null && user.getAddress().length() >= 200) {
            return false;
        }
        
        if (user.getPhone() != "" && !AccountValidatorUtil.isMobile(user.getPhone())) {
            return false;
        }
        return true;
    }
}