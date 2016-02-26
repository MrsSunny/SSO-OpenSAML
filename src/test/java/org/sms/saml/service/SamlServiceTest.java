package org.sms.saml.service;

import static org.junit.Assert.assertNotNull;

import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.validation.ValidationException;

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
    X509Certificate request = samlService.getX509Certificate("");
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
  
//  @Test
  public void testBuildArtifact() {
    String artifactId = UUID.randomUUID().toString().replace("-", "");
    String samlArtifact = samlService.buildArtifact(artifactId);
    assertNotNull("response is null", samlArtifact);
  }
  
//  @Test
  public void testGetRSAPublicKey() {
    String idpXmlFile = "/opensaml/SPSSODescriptor.xml";
    samlService.getRSAPublicKey(idpXmlFile);
  }
  
//  @Test
  public void testGetRSAPrivateKey() {
    String spXmlFile = "/opensaml/IDPSSODescriptor.xml";
    samlService.getRSAPrivateKey(spXmlFile);
  }
  
//  @Test
  public void testSignature() {
    String spXmlFile = "/opensaml/IDPSSODescriptor.xml";
    Response response = samlService.getResponse();
    PublicKey publicKey = samlService.getRSAPublicKey(spXmlFile);
    BasicX509Credential publicCredential = new BasicX509Credential();
    publicCredential.setPublicKey(publicKey);
    SignatureValidator signatureValidator = new SignatureValidator(publicCredential);
    Signature signature = response.getSignature();
    try {
      signatureValidator.validate(signature);
    } catch (ValidationException e) {
      e.printStackTrace();
    }
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