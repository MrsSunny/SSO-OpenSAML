package org.sms.saml.service;

import static org.junit.Assert.assertNotNull;
import java.util.UUID;
import org.junit.Test;
import base.BaseTest;

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
  
  @Test
  public void testGetRSAPublicKey() {
    String idpXmlFile = "/opensaml/IDPSSODescriptor.xml";
    samlService.getRSAPublicKey(idpXmlFile);
  }
  
  @Test
  public void testGetRSAPrivateKey() {
    String spXmlFile = "/opensaml/SPSSODescriptor.xml";
    samlService.getRSAPrivateKey(spXmlFile);
  }
}