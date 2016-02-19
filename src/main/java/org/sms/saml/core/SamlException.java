package org.sms.saml.core;

public class SamlException extends Exception {
  private static final long serialVersionUID = 4559202031691480567L;

  public SamlException(String message, Exception e) {
    super(message, e);
  }

  public SamlException(String message) {
    super(message);
  }

  public SamlException(Exception e) {
    super(e);
  }
}
