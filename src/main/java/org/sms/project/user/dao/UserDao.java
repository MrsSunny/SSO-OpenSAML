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
 * @Title: SysUserDao.java
 * @author Sunny
 * @date 2015年5月5日 下午1:44:57
 * @version 2.0.0
 */
@Repository
public class UserDao {

  @Autowired
  private SqlSessionTemplate jdbcTemplate;

  public Long insert(User user) {
    return user.getId();
  }

  public void update(User user) {
    jdbcTemplate.update("1", user);
  }

  public void delete(long id) {
    jdbcTemplate.delete("1", id);
  }

  public List<User> queryByCondition(String query, String order, int startIndex, int size) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("query", query);
    map.put("order", order);
    return jdbcTemplate.selectList("1", map, new RowBounds(startIndex - 1, size));
  }

  public User find(Long id) {
    return jdbcTemplate.selectOne("get", id);
  }
  
  public User findUserByLogin_Id(String loign_id) {
    return jdbcTemplate.selectOne("getByLoginId", loign_id);
  }
}