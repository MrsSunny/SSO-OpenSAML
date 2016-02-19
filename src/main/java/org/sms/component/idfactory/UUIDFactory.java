package org.sms.component.idfactory;


/**
 * @author zhenxing.Liu
 */
public enum UUIDFactory implements KeyFactory {
  
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
