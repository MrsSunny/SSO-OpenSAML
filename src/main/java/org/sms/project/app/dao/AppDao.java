package org.sms.project.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.sms.SysConstants;
import org.sms.project.app.entity.App;
import org.sms.project.helper.ben.AutoBuildBean;
import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class AppDao {

  @Autowired
  private SysJdbcTemplate sysJdbcTemplate;

  /**
   * @param id
   * @return
   */
  public App findAppById(long id) {
    return sysJdbcTemplate.queryForObject("SELECT ID,APP_DOMAIN,CREATE_DATE,APP_INDEX FROM SYS_APP WHERE ID = ?", new Object[] { id }, new RowMapper<App>() {
      @Override
      public App mapRow(ResultSet rs, int rowNum) throws SQLException {
        App app = new App();
        AutoBuildBean.INSTANCE.buildBean(app, rs);
        return app;
      }
    });
  }

  /**
   * @param appName
   * @return
   */
  public App findAppByAppName(String appName) {
    return sysJdbcTemplate.queryForObject("SELECT * FROM SYS_APP WHERE APP_DOMAIN = ?", new Object[] { appName }, new RowMapper<App>() {
      @Override
      public App mapRow(ResultSet rs, int rowNum) throws SQLException {
        App app = new App();
        AutoBuildBean.INSTANCE.buildBean(app, rs);
        return app;
      }
    });
  }

  /**
   * @param app
   * @return
   */
  public int insert(App app) {

    String sql = "INSERT INTO SYS_APP(ID, APP_DOMAIN, USABLE_STATUS, CREATE_USER_ID) VALUES(?,?,?,?)";
    Object[] params = new Object[] { app.getId(), app.getApp_domain(), SysConstants.ENABLE, app.getCreate_user_id() };
    int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param query
   * @param order
   * @param startIndex
   * @param size
   * @return
   */
  public List<App> getApp(String query, String order, int startIndex, int size) {
    String sql = "SELECT * FROM SYS_APP";
    List<App> list = sysJdbcTemplate.queryPage(sql, startIndex, size);
    return list;
  }

  /**
   * @param roleUser
   * @return
   */
  public int update(App app) {
    String sql = "UPDATE ROLE SET app_domain =?, usable_status = ?, modify_date = ?, modify_user_id = ?, app_index=?  WHERE id = ?";
    Object[] params = new Object[] { app.getApp_domain(), app.getUsable_status(), app.getModify_date(), app.getModify_user_id(), app.getApp_index(),
        app.getId() };
    int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param id
   * @return
   */
  public int delete(long id) {
    String sql = "DELETE FROM ROLE WHERE id = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.INTEGER };
    return sysJdbcTemplate.update(sql, params, types);
  }
}