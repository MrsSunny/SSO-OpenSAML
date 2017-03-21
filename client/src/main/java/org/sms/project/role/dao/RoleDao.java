package org.sms.project.role.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.sms.SysConstants;
import org.sms.project.helper.ben.BeanHelper;
import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.sms.project.role.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
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
   * @param role
   * @return
   */
  public int insert(Role role) {
    String sql = "INSERT INTO ROLE(ID, NAME, DESCRIPTION, USABLE_STATUS, CREATE_USER_ID) VALUES(?,?,?,?,?)";
    Object[] params = new Object[] { role.getId(), role.getName(), role.getDescription(), SysConstants.ENABLE, role.getCreate_user_id() };
    int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT };
    return sysJdbcTemplate.update(sql, params, types);
  }
  
  public List<String> getRoleNameByEmail(long id) {
    List<String> roleNames = sysJdbcTemplate.query("SELECT ROLE.NAME FROM ROLE_USER  LEFT JOIN ROLE ON ROLE_USER.ROLE_ID = ROLE.ID where ROLE_USER.USER_ID = " + id, new RowNameRowMapper());
    return roleNames;
  }
  
  private class RowNameRowMapper implements RowMapper<String> {
    
    @Override
    public String mapRow(ResultSet rs, int i) throws SQLException {
      return rs.getString("name");
    }
  }
  /**
   * @param id
   * @return
   */
  public int delete(long id) {
    String sql = "DELETE FROM ROLE WHERE ID = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.INTEGER };
    return sysJdbcTemplate.update(sql, params, types);
  }

  /**
   * @param role
   * @return
   */
  public int update(Role role) {
    String sql = "UPDATE ROLE SET NAME =?, DESCRIPTION =?, USABLE_STATUS = ?, MODIFY_DATE = ?, MODIFY_USER_ID = ?  WHERE ID = ?";
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
    return sysJdbcTemplate.queryForObject("SELECT ID, NAME, CREATE_DATE,DESCRIPTION FROM ROLE WHERE ID = ?", new Object[] { id }, new RowMapper<Role>() {
      @Override
      public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        BeanHelper.INSTANCE.buildBean(role, rs);
        return role;
      }
    });
  }
}