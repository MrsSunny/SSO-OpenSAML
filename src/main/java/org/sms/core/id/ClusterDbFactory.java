package org.sms.core.id;

import java.util.List;
import java.util.UUID;
import org.sms.core.hash.ConsistentHash;
import org.sms.core.hash.impl.ConsistentHashImpl;
import org.sms.project.datasource.SysDataSource.Node;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Sunny
 */
public enum ClusterDbFactory implements KeyFactory {
  
  INSTANCE;
  
  private ConsistentHash<Node> consistentHash;
  
  public void setSourceHash(List<Node> nodes) {
    consistentHash = new ConsistentHashImpl<Node>(1, nodes);
    consistentHash.addListMember();
  }
  
  public int getSourceListSize() {
    return consistentHash.getAllMembers().size();
  }

  @Override
  public long getPrimaryKey(String tableName) {
    Node node = consistentHash.getMember(UUID.randomUUID().toString());
    JdbcTemplate jdbcTemplate = new JdbcTemplate(node.getDriverManagerDataSource());
    System.out.println(jdbcTemplate);
    return 0;
  }

  @Override
  public long getPrimaryKey(String groupName, String tableName) {
    return 0;
  }
}