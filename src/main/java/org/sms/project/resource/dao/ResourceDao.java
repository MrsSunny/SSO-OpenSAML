package org.sms.project.resource.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.sms.project.helper.jdbc.SysJdbcTemplate;
import org.sms.project.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class ResourceDao {

  @Autowired
  private SysJdbcTemplate sysJdbcTemplate;

  public List<Resource> getResources(String query, String order, int startIndex, int size) {
    String sql = "SELECT * FROM RESOURCE";
    List<Resource> list = sysJdbcTemplate.querySP(sql, startIndex, size);
    return list;
  }

  public List<ResourceMapping> getResourceMapping() {
    List<ResourceMapping> resourceMappings = sysJdbcTemplate.query("select resource.url, role.name from role_resource left join resource on role_resource.resource_id = resource.id left join role on role_resource.role_id = role.id", new ResourceMappingRowMapper());
    return resourceMappings;
  }

  public int insert(Resource resources) {
    String sql = "INSERT INTO RESOURCE(ID, URL, TYPE, NAME, DESCRIPTION, USABLE_STATUS, CREATE_USER_ID) VALUES(?,?,?,?,?,?,?)";
    Object[] params = new Object[] { resources.getId(), resources.getUrl(), resources.getType(), resources.getName(), resources.getDescription(), 1, resources.getCreate_user_id() };
    int[] types = new int[] { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR , Types.VARCHAR, Types.VARCHAR, Types.BIGINT};
    return sysJdbcTemplate.update(sql, params, types);
  }

  public int update(Resource resources) {
    String sql = "UPDATE RESOURCE SET type = ?, name = ?, description = ?, usable_status = ? ,modify_date = ?, modify_user_id = ?  WHERE id = ?";
    Object[] params = new Object[] { resources.getType(), resources.getName(), resources.getDescription(), resources.getUsable_status(), resources.getModify_date(), resources.getModify_user_id(), resources.getId() };
    int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT};
    return sysJdbcTemplate.update(sql, params, types);
  }

  public int delete(long id) {
    String sql = "DELETE FROM RESOURCE WHERE id = ?";
    Object[] params = new Object[] { id };
    int[] types = new int[] { Types.INTEGER };
    return sysJdbcTemplate.update(sql, params, types);
  }

  public Resource findById(long id) {
    final Resource resource = new Resource();
    sysJdbcTemplate.query("SELECT NAME, ID,URL,CREATE_DATE,DESCRIPTION,PARENT_ID, TYPE FROM RESOURCE WHERE id = ?", new Object[] { id }, new RowCallbackHandler() {
      public void processRow(ResultSet rs) throws SQLException {
        resource.setName(rs.getString("name"));
        resource.setId(rs.getLong("id"));
        resource.setUrl(rs.getString("url"));
        resource.setCreate_date(rs.getTimestamp("create_date"));
        resource.setDescription(rs.getString("description"));
        resource.setParentId(rs.getLong("parent_id"));
        resource.setType(rs.getString("type"));
      }
    });
    return resource;
  }

  private class ResourceMappingRowMapper implements RowMapper<ResourceMapping> {
    @Override
    public ResourceMapping mapRow(ResultSet rs, int i) throws SQLException {
      ResourceMapping resourceMapping = new ResourceMapping();
      resourceMapping.setUrl(rs.getString("url"));
      resourceMapping.setRole_name(rs.getString("name"));
      return resourceMapping;
    }
  }

  public class ResourceMapping {
    private String url;

    private String role_name;

    public final String getUrl() {
      return url;
    }

    public final void setUrl(String url) {
      this.url = url;
    }

    public final String getRole_name() {
      return role_name;
    }

    public final void setRole_name(String role_name) {
      this.role_name = role_name;
    }
  }
}