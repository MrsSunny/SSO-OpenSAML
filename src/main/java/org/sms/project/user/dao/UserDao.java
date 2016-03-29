package org.sms.project.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.sms.SysConstants;
import org.sms.project.helper.ben.BeanHelper;
import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class UserDao {

  @Autowired
  private SysJdbcTemplate sysJdbcTemplate;

  public int insert(User user) {
    String sql = "INSERT INTO USER(ID, NAME, IMAGE_PATH, PASSWORD, EMAIL, PHONE, ADRESS, CONFIRMNUM, LOGIN_SUM, LAST_LOGIN_IP, USABLE_STATUS, TOKEN, LOGIN_TYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] params = new Object[] { user.getId(), user.getName(), user.getImage_path(), user.getPassword(), user.getEmail(), user.getPhone(),
        user.getAdress(), user.getConfirmnum(), user.getLogin_sum(), user.getLast_login_ip(), SysConstants.ENABLE, user.getToken(), user.getLogin_type() };
    int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
        Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
    return sysJdbcTemplate.update(sql, params, types);
  }

  public int update(User user) {
    String sql = "UPDATE USER SET NAME = ?, IMAGE_PATH = ?, PHONE = ? , ADRESS = ?,LAST_LOGIN_IP = ?, MODIFY_DATE =?  WHERE ID = ?";
    Object[] params = new Object[] { user.getName(), user.getImage_path(), user.getPhone(), user.getAdress(), user.getLast_login_ip(), user.getModify_date(),
        user.getId() };
    int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  public int delete(long id) {
    String sql = "DELETE FROM USER WHERE ID = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.INTEGER };
    return sysJdbcTemplate.update(sql, params, types);
  }

  public List<User> queryByCondition(String query, String order, int startIndex, int size) {
    String sql = "SELECT * FROM USER";
    List<User> list = sysJdbcTemplate.queryPage(sql, startIndex, size);
    return list;
  }

  public User findById(Long id) {
    try {
      return sysJdbcTemplate.queryForObject("SELECT ID, NAME, EMAIL, PHONE, ADRESS, CREATE_DATE FROM USER WHERE ID = ?", new Object[] { id },
          new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
              User user = new User();
              BeanHelper.INSTANCE.buildBean(user, rs);
              return user;
            }
          });
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public User findUserByEmail(String email) {
    try {
      return sysJdbcTemplate.queryForObject("SELECT ID, NAME, PASSWORD,EMAIL, PHONE, ADRESS, USABLE_STATUS, CREATE_DATE FROM USER WHERE EMAIL = ?", new Object[] { email },
          new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) {
              User user = new User();
              BeanHelper.INSTANCE.buildBean(user, rs);
              return user;
            }
          });
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public int lockUser(long id) {
    String sql = "UPDATE USER SET USABLE_STATUS = " + SysConstants.DISABLE + "  WHERE ID = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  public boolean isExit(String email) {
    return this.findUserByEmail(email) == null;
  }
}