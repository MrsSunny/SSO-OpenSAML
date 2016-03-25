package org.sms.project.role.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.sms.SysConstants;
import org.sms.project.helper.ben.AutoBuildBean;
import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.sms.project.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * @author Sunny
 */
public class RoleDao {

  @Autowired
  private SysJdbcTemplate sysJdbcTemplate;

  /**
   * @param query
   * @param order
   * @param startIndex
   * @param size
   * @return
   */
  public List<Role> getRole(String query, String order, int startIndex, int size) {
    String sql = "SELECT * FROM ROLE";
    List<Role> list = sysJdbcTemplate.queryPage(sql, startIndex, size);
    return list;
  }

  /**
   * insert_descriptions:
   * 
   * @param role
   * @return
   */

  public int insert(Role role) {
    String sql = "INSERT INTO ROLE(ID, NAME, DESCRIPTION, USABLE_STATUS, CREATE_USER_ID) VALUES(?,?,?,?,?)";
    Object[] params = new Object[] { role.getId(), role.getName(), role.getDescription(), SysConstants.ENABLE, role.getCreate_user_id() };
    int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
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

  /**
   * @param role
   * @return
   */
  public int update(Role role) {
    String sql = "UPDATE ROLE SET name =?, description =?, usable_status = ?, modify_date = ?, modify_user_id = ?  WHERE id = ?";
    Object[] params = new Object[] { role.getName(), role.getDescription(), role.getUsable_status(), role.getModify_date(), role.getModify_user_id(),
        role.getId() };
    int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param id
   * @return
   */
  public Role findById(long id) {
    final Role role = new Role();
    sysJdbcTemplate.query("SELECT ID, NAME, CREATE_DATE,DESCRIPTION FROM ROLE WHERE id = ?", new Object[] { id }, new RowCallbackHandler() {
      public void processRow(ResultSet rs) throws SQLException {
        AutoBuildBean.INSTANCE.buildBean(role, rs);
      }
    });
    return role;
  }
}