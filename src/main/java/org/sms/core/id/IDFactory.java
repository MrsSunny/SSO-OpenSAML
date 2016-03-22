package org.sms.core.id;

import org.sms.core.id.KeyFactory.PrimaryKeyType;

/**
 * @author Sunny
 */
public enum IDFactory {

  INSTANCE;
  
  public Long getId(PrimaryKeyType typeName, String tableName) {
    
    KeyFactory factory = null;
    switch (typeName) {
    case CLUSTERDB:
      factory = ClusterDbFactory.INSTANCE;
      break;
    case AUTO:
      break;
    case CACHEDB:
      factory = CacheDbFactory.INSTANCE;
      break;
    default:
      throw new RuntimeException("不能解析的主键获取类型");
    }
    return factory.getPrimaryKey(tableName);
  }
}
