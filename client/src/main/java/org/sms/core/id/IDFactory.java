package org.sms.core.id;

import org.sms.core.id.KeyFactory.PrimaryKeyType;

/**
 * @author Sunny
 */
public enum IDFactory {

  INSTANCE;
  
  /**
   * 默认获取ID的方式为CLUSTERDB方式（数据库集群提供ID）
   * @param tableName 根据那种表来获取最新的ID
   * @return
   */
  public Long getId(String tableName) {
    return getId(PrimaryKeyType.AUTO, tableName);
  }

  public Long getId(PrimaryKeyType typeName, String tableName) {

    KeyFactory factory = null;
    switch (typeName) {
    case CLUSTERDB:
      factory = ClusterDbFactory.INSTANCE;
      return factory.getPrimaryKey(tableName);
    case AUTO:
      break;
    default:
      throw new RuntimeException("不能解析的主键获取类型");
    }
    return null;
  }
}