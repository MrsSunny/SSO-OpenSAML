package org.sms.component.idfactory;

import java.util.UUID;


/**
 * @author zhenxing.Liu
 */
public enum UUIDFactory {
  
  INSTANCE;
  
  public synchronized String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
