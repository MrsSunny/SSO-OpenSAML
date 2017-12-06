package org.sms.project.roleresource.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.roleresource.entity.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class RoleResourceDao {

  @Autowired
  private SqlSession sqlSession;

  public int insert(RoleResource roleResource) {
    return sqlSession.insert(this.getClass().getName() + ".insert", roleResource);
  }

  public int update(RoleResource roleResource) {
    return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", roleResource);
  }

  public int delete(long id) {
    return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
  }

  public RoleResource findById(Long id) {
    return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
  }
}