package org.sms.project.blog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.blog.entity.Blog;
import org.sms.project.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class BlogDao {

    @Autowired
    private SqlSession sqlSession;

    public int insert(Blog blog) {
        return sqlSession.insert(this.getClass().getName() + ".insert", blog);
    }

    public int update(Blog blog) {
        return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", blog);
    }

    public int delete(long id) {
        return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
    }

    public Blog findById(Long id) {
        return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
    }

    public int getCount() {
        return sqlSession.selectOne(this.getClass().getName() + ".selectCount");
    }

    public List<Blog> queryByCondition(String query, String order, Page page) {
        return sqlSession.selectList(this.getClass().getName() + ".selectByPage", page);
    }
}