package org.sms.project.blog.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.blog.entity.Blog;
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
}