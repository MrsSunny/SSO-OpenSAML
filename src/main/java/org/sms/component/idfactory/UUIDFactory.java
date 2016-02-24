package org.sms.component.idfactory;

import java.util.UUID;


/**
 * @author zhenxing.Liu
 */
public enum UUIDFactory {
  
  INSTANCE;
  
  public String getUUID(String tableName) {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
