package org.sms.project.user.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.sms.project.page.Page;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class UserDao {

    @Autowired
    private SqlSession sqlSession;

    public long insert(User user) {
        return sqlSession.insert(this.getClass().getName() + ".insert", user);
    }

    public int update(User user) {
        return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", user);
    }

    public int delete(long id) {
        return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
    }

    public User findById(Long id) {
        return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
    }

    public User findUserByEmail(String email) {
        return sqlSession.selectOne(this.getClass().getName() + ".selectByEmail", email);
    }
    
    public int findUserByEmailAndName(String email, String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        return sqlSession.selectOne(this.getClass().getName() + ".selectByEmailAndName", user);
    }

    public int lockUser(long id) {
        return 0;
    }

    public boolean isExit(String email) {
        return this.findUserByEmail(email) == null;
    }

    public int getCount() {
        return sqlSession.selectOne(this.getClass().getName() + ".selectCount");
    }

    public List<User> queryByCondition(String query, String order, Page page) {
        return sqlSession.selectList(this.getClass().getName() + ".selectByPage", page);
    }
}