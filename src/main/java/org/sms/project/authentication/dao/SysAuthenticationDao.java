package org.sms.project.authentication.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.sms.project.authentication.entity.SysAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class SysAuthenticationDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * @param sysAuthentication
   * @return
   */
  public int create(SysAuthentication sysAuthen) {
    String sql = "INSERT INTO SYS_AUTHENTICATION(ID,SSO_TOKEN,AUTHENTICATION_TIME, SUBJECT_ID ,END_TIME) VALUES(?,?,?,?,?)";
    Object[] params = new Object[] { sysAuthen.getId(), sysAuthen.getSso_token(), sysAuthen.getAuthentication_time(), sysAuthen.getSubject_id(),
        sysAuthen.getExpire_time() };
    int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.TIMESTAMP };
    return jdbcTemplate.update(sql, params, types);
  }

  /**
   * @param id
   * @return
   */
  public SysAuthentication queryById(String SSOToken) {
    final SysAuthentication sysAuthen = new SysAuthentication();
    jdbcTemplate.query("SELECT * FROM SYS_AUTHENTICATION WHERE SSO_TOKEN = ?", new Object[] { SSOToken }, new RowCallbackHandler() {
      public void processRow(ResultSet rs) throws SQLException {
        sysAuthen.setId(rs.getLong("id"));
        sysAuthen.setSso_token(rs.getString("sso_token"));
        sysAuthen.setAuthentication_time(rs.getTimestamp("authentication_time"));
        sysAuthen.setSubject_id(rs.getString("subject_id"));
        sysAuthen.setExpire_time(rs.getTimestamp("end_time"));
      }
    });
    return sysAuthen;
  }
}