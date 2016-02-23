package org.sms.project;

/**
 * 
 * class_descriptions:
 * @author Sunny
 * @since 1.8.0
 */

public interface SysConstants {
  
  public static final String LOGIN_USER = "user";
  public static final String ENABLE = "0";
  
  public enum LockType {
    
    LOCKED("0"), UNLOCKED("1");
    
    private String lockType;
    
    private LockType(String lockType) {
      this.lockType = lockType;
    }
    
    public String getLockType() {
      return this.lockType;
    }
  }
}
