package org.sms.project.sso;

import java.util.HashMap;
import java.util.Map;
import org.opensaml.saml2.core.Response;

/**
 * @author Sunny
 * @since 1.8.0
 */
public enum SSOHelper {
  
  INSTANCE;

  private static final Map<String, Response> ARTIFACT_RESPONSE = new HashMap<String, Response>();
  
  public void put(String artifactId, Response response) {
    if (null != artifactId && null != response) {
      ARTIFACT_RESPONSE.put(artifactId, response);
    }
  }
  
  public Response get(String artifactId) {
    return null == artifactId ? null : ARTIFACT_RESPONSE.get(artifactId);
  }
  
  public void remove(String artifactId) {
    ARTIFACT_RESPONSE.remove(artifactId);
  }
}