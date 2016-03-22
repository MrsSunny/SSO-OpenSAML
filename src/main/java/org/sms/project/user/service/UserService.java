package org.sms.project.user.service;

import java.util.List;

import org.sms.project.user.entity.User;

/**
 * @Title: ISysUser.java
 * @Description: TODO
 * @author <a href="https://github.com/MrsSunny">Sunny</a>
 * @date 2015年5月1日 下午7:35:14
 * @version 2.0.0
 */
public interface UserService {

    long insert(User user);

    void update(User user);

    void delete(long id);

    User find(long id);
    
    User find(String login_id);

    List<User> queryByCondition(String query, String order, int startIndex, int size);
    
    User findUserByLogin_Id(String loign_id);
}