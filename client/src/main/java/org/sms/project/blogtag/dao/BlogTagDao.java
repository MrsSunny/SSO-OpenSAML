package org.sms.project.blogtag.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.blogtag.entity.BlogTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class BlogTagDao {

  @Autowired
  private SqlSession sqlSession;

  public int insert(BlogTag blogTag) {
    return sqlSession.insert(this.getClass().getName() + ".insert", blogTag);
  }

  public int update(BlogTag blogTag) {
    return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", blogTag);
  }

  public int delete(long id) {
    return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
  }

  public BlogTag findById(Long id) {
    return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
  }
}