package org.sms.component.idfactory;

/**
 * @author Sunny
 */
public enum ClusterDbFactory implements KeyFactory {
  
  INSTANCE;

  @Override
  public long getPrimaryKey(String tableName) {
    return 0;
  }

  @Override
  public long getPrimaryKey(String groupName, String tableName) {
    return 0;
  }
}