package org.sms.project.resource.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class ResourceDao {

  @Autowired
  private SqlSession sqlSession;

  public int insert(Resource resource) {
    return sqlSession.insert(this.getClass().getName() + ".insert", resource);
  }

  public int update(Resource resource) {
    return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", resource);
  }

  public int delete(long id) {
    return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
  }

  public Resource findById(Long id) {
    return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
  }
}