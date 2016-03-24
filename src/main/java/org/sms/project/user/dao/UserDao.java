package org.sms.project.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class UserDao {

  @Autowired
  private SqlSessionTemplate sqlSession;

  public Long insert(User user) {
    sqlSession.insert("insert", user);
    return user.getId();
  }

  public int update(User user) {
    System.out.println(user.getId());
    return sqlSession.update("update", user);
  }

  public int delete(long id) {
    return sqlSession.delete("delete", id);
  }

  public List<User> queryByCondition(String query, String order, int startIndex, int size) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("query", query);
    map.put("order", order);
    return sqlSession.selectList("list", map, new RowBounds(startIndex - 1, size));
  }

  public User findById(Long id) {
    return sqlSession.selectOne("get", id);
  }
  
  public User findUserByLoginId(String loign_id) {
    return sqlSession.selectOne("getByLoginId", loign_id);
  }
}