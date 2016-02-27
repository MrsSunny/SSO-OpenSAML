package org.sms.saml.service;

import java.util.UUID;
import javax.xml.namespace.QName;
import org.junit.Test;
import base.BaseTest;
import static org.junit.Assert.assertNotNull;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.signature.X509Certificate;

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
    X509Certificate request = samlService.getX509Certificate("");
    assertNotNull("response is null", request);
  }

  /**
   * Test method for {@link org.sms.saml.service.SamlService#getResponse()}.
   * @throws ConfigurationException 
   */
  @Test
  public void testBuildResponse() throws ConfigurationException {
    AuthnRequest request = samlService.getRequest();
    String requestID = request.getID();
    Conditions conditions = request.getConditions();
    AudienceRestriction audienceRestriction = conditions.getAudienceRestrictions().get(0);
    Audience audience = audienceRestriction.getAudiences().get(0);
    String audienceURI = audience.getAudienceURI();
    String samlResponse = samlService.buildResponse(requestID, audienceURI);
    assertNotNull("response is null", samlResponse);
  }
  
//  @Test
  public void testBuildArtifact() {
    String artifactId = UUID.randomUUID().toString().replace("-", "");
    String samlArtifact = samlService.buildArtifact(artifactId);
    assertNotNull("response is null", samlArtifact);
  }
  
//  @Test
  public void testGetRSAPublicKey() {
    samlService.getRSAPublicKey();
  }
  
//  @Test
  public void testGetRSAPrivateKey() {
    samlService.getRSAPrivateKey();
  }
  
//  @Test
  public void testSignature() {
//    String spXmlFile = "/opensaml/IDPSSODescriptor.xml";
//    Response response = samlService.getResponse();
//    PublicKey publicKey = samlService.getRSAPublicKey(spXmlFile);
//    BasicX509Credential publicCredential = new BasicX509Credential();
//    publicCredential.setPublicKey(publicKey);
//    SignatureValidator signatureValidator = new SignatureValidator(publicCredential);
//    Signature signature = response.getSignature();
//    try {
//      signatureValidator.validate(signature);
//    } catch (ValidationException e) {
//      e.printStackTrace();
//    }
  }
  
//  @Test
  public void testValiatSignature() {
//    String spXmlFile = "/opensaml/IDPSSODescriptor.xml";
//    samlService.getRSAPrivateKey(spXmlFile);
  }
  
//  @Test
  public void testEncrypt() throws NoSuchAlgorithmException, KeyException, EncryptionException {
//    String spXmlFile = "/opensaml/SPSSODescriptor.xml";
//    Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
//    BasicX509Credential publicCredential = new BasicX509Credential();
//    publicCredential.setPublicKey(samlService.getRSAPublicKey(spXmlFile));
//    EncryptedAssertion encryptedAssertion = samlService.encrypt(assertion, publicCredential);
//    System.out.println(encryptedAssertion);
  }
  
  
//  @Test
  public void testDecrypt() {
//    String spXmlFile = "/opensaml/SPSSODescriptor.xml";
//    samlService.getRSAPrivateKey(spXmlFile);
  }
  
  public XMLObject buildXMLObject(QName objectQName) {
    XMLObjectBuilder<?> builder = Configuration.getBuilderFactory().getBuilder(objectQName);
    return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }
}