package org.sms.project.roleuser.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.sms.SysConstants;
import org.sms.project.helper.ben.AutoBuildBean;
import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.sms.project.roleuser.entity.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class RoleUserDao {
  
  @Autowired
  private SysJdbcTemplate sysJdbcTemplate;

  /**
   * @param query
   * @param order
   * @param startIndex
   * @param size
   * @return
   */
  public List<RoleUser> getRoleUser(String query, String order, int startIndex, int size) {
    String sql = "SELECT * FROM ROLE_USER";
    List<RoleUser> list = sysJdbcTemplate.queryPage(sql, startIndex, size);
    return list;
  }

  /**
   * @param roleUser
   * @return
   */
  public int insert(RoleUser roleUser) {
    String sql = "INSERT INTO ROLE_USER(ID, USER_ID, ROLE_ID, USABLE_STATUS, CREATE_USER_ID) VALUES(?,?,?,?,?)";
    Object[] params = new Object[] { roleUser.getId(), roleUser.getUser_id(), roleUser.getRole_id(), SysConstants.ENABLE, roleUser.getCreate_user_id() };
    int[] types = new int[] { Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.VARCHAR , Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param roleUser
   * @return
   */
  public int update(RoleUser roleUser) {
    String sql = "UPDATE ROLE_USER SET usable_status = ?, modify_date = ?, modify_user_id = ?  WHERE id = ?";
    Object[] params = new Object[] { roleUser.getUsable_status(), roleUser.getModify_date(), roleUser.getModify_user_id(), roleUser.getId() };
    int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT};
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param id
   * @return
   */
  public int delete(long id) {
    String sql = "DELETE FROM ROLE_USER WHERE id = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.INTEGER };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param id
   * @return
   */
  public RoleUser findById(long id) {
    final RoleUser roleUser = new RoleUser();
    sysJdbcTemplate.query("SELECT ID, USER_ID, ROLE_ID, CREATE_DATE FROM ROLE_USER WHERE id = ?", new Object[] { id }, new RowCallbackHandler() {
      public void processRow(ResultSet rs) throws SQLException {
        AutoBuildBean.INSTANCE.buildBean(roleUser, rs);
      }
    });
    return roleUser;
  }
}