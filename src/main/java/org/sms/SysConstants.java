package org.sms;

/**
 * @author Sunny
 */
public interface SysConstants {
  
  public static final String LOGIN_USER = "user";
  public static final String ENABLE = "0";
  public static final String CHARSET = "UTF-8";
  public static final String TICKETID = "ticket";
  public static final String AUTHREQUEST = "authRequest";
  public static final String POST_METHOE = "POST";
  public static final String LOCALDOMAIN = "http://passport.soaer.com";
  public static final String SIGNATURE_METHOD = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
  public static final String CANON_ALGORITHM = "http://www.w3.org/2001/10/xml-exc-c14n#";
  public static final String IDPSSPXMLFILE = "/opensaml/IDPSSODescriptor.xml";
  public static final String SPSSPXMLFILE = "/opensaml/SPSSODescriptor.xml";
  public static final String IDPSERVERADDRESS = "http://login.soaer.com:8888";
  public static final String ARTIFACT_KEY = "artifact";
  public static final String ARTIFACT_RESOLVE_KEY = "artifactResolve";
  public static final String TOKEN_KEY = "token";
  public static final String SEND_ARTIFACT_URI = "/SAML2/sendArtifactToIDP";
  public static final String REDIRECT_URL_KEY = "redirectUrl";
  public static final String IDPRECEIVESPARTIFACT_URL = "http://passport.soaer.com:8888/SAML2/receiveSPArtifact";
  public static final String SPRECEIVESPARTIFACT_URL = "http://passport.soaer.com:8888/SAML2/receiveIDPArtifact";
  public static final String LOCALDOMAIN_SAML2_SERVICE = "http://passport.soaer.com:8888/SAML2";
  public static final String SP_ARTIFACT_RESOLUTION_SERVICE = "http://passport.soaer.com:8888/SAML2/SPArtifactResolution";
  public static final String IDP_ARTIFACT_RESOLUTION_SERVICE = "http://passport.soaer.com:8888/SAML2/IDPArtifactResolution";
  public static final String SSO_TOKEN_KEY = "idp_token";
  public static final String ACTION_KEY = "doAction";
  public static final String SAML_ID_PREFIX_CHAR = "_";
  public static final String METHOD_SPILT_CHAR = "?";
  public static final String IPDLOGIN_PAGE = "http://passport.soaer.com:8888/login.jsp";
  public static final String PARAM_VALUE = "=";
  public static final String ERROR_LOGIN = "errorLogin";
  public static final String LOGIN_PAGE = "http://passport.soaer.com:8888/loginPage";
  public static final String SP_INDEX_PAGE = "http://passport.soaer.com:8888/index.html";
  public static final String IDP_TICKET = "idp_ticket";
  public static final String DEFAULT_SP_RECEIVESPARTIFACT_URL = "http://passport.soaer.com:8888/SAML2/receiveIDPArtifact";
  public static final int DEFAULT_EXPIRE = 7;
  public static final String TICKET_SPILT = ",";
  public static final String IGNORE_KEY = "ignoreUrl";
  public static final String SYS_CONFIG = "config/sys/sysConfig.xml";
  public static final String DOMAIN = "soaer.com";
}