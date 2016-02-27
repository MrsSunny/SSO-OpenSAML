package org.sms;

/**
 * 
 * class_descriptions:
 * @author Sunny
 * @since 1.8.0
 */

public interface SysConstants {
  
  public static final String LOGIN_USER = "user";
  public static final String ENABLE = "0";
  public static final String CHARSET = "UTF-8";
  public static final String TICKETID = "ticket";
  public static final String AUTHREQUEST = "authRequest";
  public static final String LOCALDOMAIN = "http://soaer.com";
  public static String SIGNATURE_METHOD = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
  public static String CANON_ALGORITHM = "http://www.w3.org/2001/10/xml-exc-c14n#";
  public static String IDPSSPXMLFILE = "/opensaml/IDPSSODescriptor.xml";
  public static final String SPSSPXMLFILE = "/opensaml/SPSSODescriptor.xml";
  
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
