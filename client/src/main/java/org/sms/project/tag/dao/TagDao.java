package org.sms.project.tag.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.tag.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class TagDao {

  @Autowired
  private SqlSession sqlSession;

  public int insert(Tag tag) {
    return sqlSession.insert(this.getClass().getName() + ".insert", tag);
  }

  public int update(Tag tag) {
    return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", tag);
  }

  public int delete(int id) {
    return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
  }
  
  public Tag findById(int id) {
    return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
  }
}