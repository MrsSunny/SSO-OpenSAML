package org.sms.project.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.sms.project.app.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 * @since 1.8.0
 */
@Repository
public class AppDao {
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * findAppById_descriptions:
   * @param id
   * @return
   */
  public App findAppById(long id) {
    return null;
  }

  /**
   * @param appName
   * @return
   */
  public App findAppByAppName(String appName) {
    final App app = new App();
    jdbcTemplate.query("SELECT * FROM SYS_APP WHERE APP_DOMAIN = ?", new Object[] { appName }, new RowCallbackHandler() {
      public void processRow(ResultSet rs) throws SQLException {
        app.setId(rs.getLong("id"));
        app.setApp_domain(rs.getString("app_domain"));
      }
    });
    return app;
  }

  /**
   * @param app
   * @return
   */
  public int insert(App app) {
    String sql = "INSERT INTO RESOURCE(user_id,name,phone,email) VALUES(?,?,?,?)";
    Object[] params = new Object[] { "", "", "", "" };
    int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.CHAR, Types.VARCHAR };
    return jdbcTemplate.update(sql, params, types);
  }
}