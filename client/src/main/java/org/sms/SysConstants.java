package org.sms;

/**
 * @author Sunny
 */
public interface SysConstants {
  
  String LOGIN_USER = "user";
  int ENABLE = 0;
  String DISABLE = "1";
  String CHARSET = "UTF-8";
  String TICKETID = "ticket";
  String AUTHREQUEST = "authRequest";
  String POST_METHOE = "POST";
  String LOCALDOMAIN = "http://passport.soaer.com";
  String SIGNATURE_METHOD = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
  String CANON_ALGORITHM = "http://www.w3.org/2001/10/xml-exc-c14n#";
  String IDPSSPXMLFILE = "/opensaml/IDPSSODescriptor.xml";
  String SPSSPXMLFILE = "/opensaml/SPSSODescriptor.xml";
  String IDPSERVERADDRESS = "http://login.soaer.com:8888";
  String ARTIFACT_KEY = "artifact";
  String ARTIFACT_RESOLVE_KEY = "artifactResolve";
  String TOKEN_KEY = "token";
  String SEND_ARTIFACT_URI = "/SAML2/sendArtifactToIDP";
  String REDIRECT_URL_KEY = "redirectUrl";
  String SPRECEIVESPARTIFACT_URL = "http://soaer.com:8888/SAML2/receiveArtifact";
  String LOCALDOMAIN_SAML2_SERVICE = "http://passport.soaer.com:8888/SAML2";
  String SP_ARTIFACT_RESOLUTION_SERVICE = "http://passport.soaer.com:8888/SAML2/SPArtifactResolution";
  String IDP_ARTIFACT_RESOLUTION_SERVICE = "http://passport.soaer.com:8888/SAML2/IDPArtifactResolution";
  String SSO_TOKEN_KEY = "idp_token";
  String ACTION_KEY = "doAction";
  String SAML_ID_PREFIX_CHAR = "_";
  String METHOD_SPILT_CHAR = "?";
  String IPDLOGIN_PAGE = "http://passport.soaer.com:8888/login.html";
  String PARAM_VALUE = "=";
  String ERROR_LOGIN = "errorLogin";
  String LOGIN_PAGE = "http://passport.soaer.com:8888/login.html";
  String SP_INDEX_PAGE = "http://passport.soaer.com:8888/index.html";
  String DEFAULT_CUSTOMER_INDEX = "/indexPage";
  String IDP_TICKET = "idp_ticket";
  String DEFAULT_SP_RECEIVESPARTIFACT_URL = "http://passport.soaer.com:8888/SAML2/receiveIDPArtifact";
  int DEFAULT_EXPIRE = 7;
  String TICKET_SPILT = ",";
  String IGNORE_KEY = "ignoreUrl";
  String SYS_CONFIG = "config/sys/sysConfig.xml";
  String DOMAIN = "soaer.com";
  String ROLE_ADMAIN_NAME = "ADMIN";
}