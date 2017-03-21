package org.sms.core.id;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.sms.core.hash.ConsistentHash;
import org.sms.core.hash.impl.ConsistentHashImpl;
import org.sms.project.datasource.SysDataSource.Node;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.mysql.jdbc.Statement;

/**
 * @author Sunny
 */
public enum ClusterDbFactory implements KeyFactory {

  INSTANCE;

  private ConsistentHash<Node> consistentHash;

  public static final String REPLACE_INTO = "replace into ";
  public static final String DEFAULT_REPLACE_VALUE = "1";

  public void setSourceHash(List<Node> nodes) {
    consistentHash = new ConsistentHashImpl<Node>(1, nodes);
    consistentHash.addListMember();
  }

  public int getSourceListSize() {
    return consistentHash.getAllMembers().size();
  }

  @Override
  public Long getPrimaryKey(String tableName) {
    Node node = consistentHash.getMember(UUID.randomUUID().toString());
    JdbcTemplate jdbcTemplate = new JdbcTemplate(node.getDriverManagerDataSource());
    return getIdByTableName(jdbcTemplate, tableName);
  }

  public Long getIdByTableName(JdbcTemplate jdbcTemplate, String tableName) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(REPLACE_INTO + tableName + "(other) values " + "(" + "'"+ DEFAULT_REPLACE_VALUE +"'" + ")", Statement.RETURN_GENERATED_KEYS);
        return ps;
      }
    }, keyHolder);
    List<Map<String, Object>> keys = keyHolder.getKeyList();
    if (keys == null || keys.size() == 0) {
      throw new RuntimeException("从数据库获取主键ID错误");
    }
    return (Long)keys.get(0).get("GENERATED_KEY");
  }
}