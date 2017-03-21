package org.sms.project.helper;

import java.util.HashMap;
import java.util.Map;
import org.opensaml.saml2.core.AuthnRequest;

/**
 * @author Sunny
 */
public enum AuthenRequestHelper {
  
  INSTANCE;

  private static final Map<String, AuthnRequest> ARTIFACT_REQUEST = new HashMap<String, AuthnRequest>();
  
  public void put(String artifactId, AuthnRequest request) {
    if (null != artifactId && null != request) {
      ARTIFACT_REQUEST.put(artifactId, request);
    }
  }
  
  public AuthnRequest get(String artifactId) {
    return null == artifactId ? null : ARTIFACT_REQUEST.get(artifactId);
  }
  
  public void remove(String artifactId) {
    ARTIFACT_REQUEST.remove(artifactId);
  }
}
