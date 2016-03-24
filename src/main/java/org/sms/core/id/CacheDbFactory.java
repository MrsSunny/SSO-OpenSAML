package org.sms.core.id;

/**
 * @author Sunny
 */
public enum CacheDbFactory implements KeyFactory {

  INSTANCE;
  
  @Override
  public Long getPrimaryKey(String tableName) {
    return 0L;
  }
}