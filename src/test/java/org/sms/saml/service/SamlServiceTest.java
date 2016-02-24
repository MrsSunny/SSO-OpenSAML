package org.sms.saml.service;

import java.util.UUID;

import org.junit.Test;
import base.BaseTest;
import static org.junit.Assert.*;

/**
 * @author Sunny
 * @since 1.8.0
 */
public class SamlServiceTest extends BaseTest {

  private SamlService samlService = (SamlService) aCtx.getBean("samlService");
  
  /**
   * Test method for
   * {@link org.sms.saml.service.SamlService#getSAMLRequest(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testBuildRequest() {
    String request = samlService.buildRequest();
    assertNotNull("response is null", request);
  }

  /**
   * Test method for {@link org.sms.saml.service.SamlService#getResponse()}.
   */
  @Test
  public void testBuildResponse() {
    String samlResponse = samlService.buildResponse();
    assertNotNull("response is null", samlResponse);
  }
  
  @Test
  public void testBuildArtifact() {
    String artifactId = UUID.randomUUID().toString().replace("-", "");
    String samlArtifact = samlService.buildArtifact(artifactId);
    assertNotNull("response is null", samlArtifact);
  }
}