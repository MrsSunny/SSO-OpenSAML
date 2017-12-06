package org.sms.project.loginlog.dao;

import org.apache.ibatis.session.SqlSession;
import org.sms.project.loginlog.entity.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class LoginLogDao {

  @Autowired
  private SqlSession sqlSession;

  public int insert(LoginLog loginLog) {
    return sqlSession.insert(this.getClass().getName() + ".insert", loginLog);
  }

  public int update(LoginLog loginLog) {
    return sqlSession.update(this.getClass().getName() + ".updateByPrimaryKeySelective", loginLog);
  }

  public int delete(long id) {
    return sqlSession.update(this.getClass().getName() + ".deleteByPrimaryKey", id);
  }

  public LoginLog findById(Long id) {
    return sqlSession.selectOne(this.getClass().getName() + ".selectByPrimaryKey", id);
  }
}