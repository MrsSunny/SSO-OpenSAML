package org.sms.component.idfactory;

import java.util.UUID;

/**
 * @author Sunny
 */
public enum UUIDFactory {
  
  INSTANCE;
  
  public synchronized String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
