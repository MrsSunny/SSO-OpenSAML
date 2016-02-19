package org.sms.saml.core;

public final class SamlConstants {

  private SamlConstants() {
  } // Hiding utility-class constructor.

  public static final String METADATA_IDP_ENDPOINT = "/metadata/idp.xml";
  public static final String LOGOUT_ENDPOINT = "/logout/";
  public static final String SSO_ENDPOINT = "/authenticate/";

  public static final CharSequence BEGIN_CERT = "BEGIN CERTIFICATE";
  public static final CharSequence BEGIN_PRIVATE = "BEGIN RSA PRIVATE KEY";
  public static final String BEGIN_PRIVATE_FULL = "-----BEGIN RSA PRIVATE KEY-----";
  public static final String END_PRIVATE_FULL = "-----END RSA PRIVATE KEY-----";
  public static final String BEGIN_CERT_FULL = "-----BEGIN CERTIFICATE-----";
  public static final String END_CERT_FULL = "-----END CERTIFICATE-----";
}
