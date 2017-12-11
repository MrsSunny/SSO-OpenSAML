package org.sms.project.role.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.page.Page;
import org.sms.project.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class RoleDao {

    @Autowired
    private SqlSession sqlSession;

    public int insert(Role role) {
        return sqlSession.insert(this.getClass().getName() + ".insert", role);
    }

    public int update(Role role) {
        return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", role);
    }

    public int delete(long id) {
        return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
    }

    public Role findById(Long id) {
        return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
    }

    public int getCount() {
        return sqlSession.selectOne(this.getClass().getName() + ".selectCount");
    }

    public List<Role> queryByCondition(String query, String order, Page page) {
        return sqlSession.selectList(this.getClass().getName() + ".selectByPage", page);
    }
}