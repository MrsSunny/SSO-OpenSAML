package org.sms.project.roleuser.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.roleuser.entity.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class RoleUserDao {

  @Autowired
  private SqlSession sqlSession;

  public int insert(RoleUser roleUser) {
    return sqlSession.insert(this.getClass().getName() + ".insert", roleUser);
  }

  public int update(RoleUser roleUser) {
    return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", roleUser);
  }

  public int delete(long id) {
    return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
  }
  
  public RoleUser findById(Long id) {
    return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
  }
}