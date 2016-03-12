package org.sms.project.resource.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.sms.project.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Sunny
 */
@Repository
public class ResourceDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Resource> getResources() {
    List<Resource> resources = jdbcTemplate.query("SELECT * FROM RESOURCE", new UserRowMapper());
    return resources;
  }

  public List<ResourceMapping> getResourceMapping() {
    List<ResourceMapping> resourceMappings = jdbcTemplate.query("select resource.url, role.name from role_resource left join resource on role_resource.resource_id = resource.id left join role on role_resource.role_id = role.id", new ResourceMappingRowMapper());
    return resourceMappings;
  }

  public int insert(Resource resources) {
    String sql = "INSERT INTO RESOURCE(user_id,name,phone,email) VALUES(?,?,?,?)";
    Object[] params = new Object[] { "", "", "", "" };
    int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.CHAR, Types.VARCHAR };
    return jdbcTemplate.update(sql, params, types);
  }

  public int update(Resource resources) {
    String sql = "UPDATE RESOURCE SET name = ?, phone = ?, email = ? WHERE id = ?";
    Object[] params = new Object[] { "", "", "", "" };
    int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.CHAR, Types.VARCHAR };
    return jdbcTemplate.update(sql, params, types);
  }

  public int delete(Resource item) {
    String sql = "DELETE FROM RESOURCE WHERE id = ?";
    Object[] params = new Object[] { item.getId() };
    int[] types = new int[] { Types.INTEGER };
    return jdbcTemplate.update(sql, params, types);
  }

  public Resource findById(int id) {
    final Resource resource = new Resource();
    jdbcTemplate.query("SELECT * FROM RESOURCE WHERE id = ?", new Object[] { id }, new RowCallbackHandler() {
      public void processRow(ResultSet rs) throws SQLException {
        resource.setName(rs.getString("name"));
        resource.setId(rs.getLong("id"));
        resource.setUrl(rs.getString("url"));
        resource.setCreate_date(rs.getTimestamp("create_date"));
        resource.setCreate_user_id(rs.getLong("create_user_id"));
        resource.setDescription(rs.getString("description"));
        resource.setParentId(rs.getLong("parentId"));
        resource.setModify_date(rs.getTimestamp("modify_date"));
        resource.setModify_user_id(rs.getLong("modify_user_id"));
        resource.setType(rs.getString("type"));
        resource.setUsable_status(rs.getString("usable_status"));
      }
    });
    return resource;
  }

  private class UserRowMapper implements RowMapper<Resource> {
    @Override
    public Resource mapRow(ResultSet rs, int i) throws SQLException {
      Resource resource = new Resource();
      resource.setName(rs.getString("name"));
      resource.setId(rs.getLong("id"));
      resource.setUrl(rs.getString("url"));
      resource.setCreate_date(rs.getTimestamp("create_date"));
      resource.setCreate_user_id(rs.getLong("create_user_id"));
      resource.setDescription(rs.getString("description"));
      resource.setParentId(rs.getLong("parentId"));
      resource.setModify_date(rs.getTimestamp("modify_date"));
      resource.setModify_user_id(rs.getLong("modify_user_id"));
      resource.setType(rs.getString("type"));
      resource.setUsable_status(rs.getString("usable_status"));
      return resource;
    }
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