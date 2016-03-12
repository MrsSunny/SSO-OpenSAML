package com.test.saml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.Security;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.opensaml.xml.util.XMLHelper;
import org.sms.saml.service.SamlService;
import org.sms.saml.service.impl.SamlServiceImpl;
import org.sms.util.GZipUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Sunny

 */
public class TestRequest {

  protected static XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();;
  /** Credential used for signing. */

  /** Builder of Assertions. */
  private AssertionBuilder assertionBuilder;

  /** Builder of Issuers. */
  private IssuerBuilder issuerBuilder;

  /** Builder of AuthnStatements. */
  private AuthnStatementBuilder authnStatementBuilder;

  /** Builder of AuthnStatements. */

  /** Generator of element IDs. */

  static SamlService saml = new SamlServiceImpl();

  static {
    try {
      DefaultBootstrap.bootstrap();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    Security.addProvider(new BouncyCastleProvider());
  }

  public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, MarshallingException, SignatureException, ConfigurationException {
    String xmlRequestBase64 = "";
    AuthnRequest request = null;
    try {
      byte[] check = Base64.decodeBase64(xmlRequestBase64);
      String gunZip = GZipUtil.gunzip(new String(check));
      request = (AuthnRequest) unmarshallElement(gunZip);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String requestID = request.getID();
    String serviceUrl = request.getAssertionConsumerServiceURL();
    Conditions conditions = request.getConditions();
    AudienceRestriction audienceRestriction = conditions.getAudienceRestrictions().get(0);
    Audience audience = audienceRestriction.getAudiences().get(0);
    String audienceURI = audience.getAudienceURI();
    System.out.println("Request ID为：" + requestID);
    System.out.println("serviceUrl 为：" + serviceUrl);
    System.out.println("audienceURI 为：" + audienceURI);
//    String xmlResponseBase64 = saml.buildResponse(requestID, audienceURI);
//    boolean isCheck = saml.validate(xmlResponseBase64);
//    System.out.println(isCheck);
//    Response response = null;
//    try {
//      byte[] check = Base64.decodeBase64(xmlResponseBase64);
//      String gunZip = GZipUtil.gunzip(new String(check));
//      response = (Response) unmarshallElement(gunZip);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    BasicCredential basicCredential = new BasicCredential();
//    basicCredential.setPublicKey(saml.getRSAPublicKey());
//    SignatureValidator signatureValidator = new SignatureValidator(basicCredential);
//    Assertion fristAssertion = response.getAssertions().get(0);
//    Signature signature = fristAssertion.getSignature();
//    try {
//      signatureValidator.validate(signature);
//      System.out.println("验证痛过");
//    } catch (ValidationException e) {
//      throw new RuntimeException("验证签名错误");
//    }
  }

  public static XMLObject buildXMLObject(QName objectQName) {
    XMLObjectBuilder<?> builder = Configuration.getBuilderFactory().getBuilder(objectQName);
    return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }

  /**
   * @param elementFile
   * @return
   */
  protected static XMLObject unmarshallElement(String elementFile) {
    try {
      BasicParserPool parser;
      parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      Document doc = (Document) parser.parse(new ByteArrayInputStream(elementFile.getBytes()));
      Element samlElement = (Element) doc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
      if (null == unmarshaller)
        throw new RuntimeException("unmarshaller的值为空");
      return unmarshaller.unmarshall(samlElement);
    } catch (XMLParserException e) {
      e.printStackTrace();
    } catch (UnmarshallingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void testSign() throws MarshallingException, SignatureException {

    assertionBuilder = (AssertionBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
    issuerBuilder = (IssuerBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
    authnStatementBuilder = (AuthnStatementBuilder) builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
    SignatureBuilder signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
    DateTime now = new DateTime();
    Assertion assertion = assertionBuilder.buildObject();
    assertion.setVersion(SAMLVersion.VERSION_20);
    assertion.setID("fghjkyuiopghjklnm,.");
    assertion.setIssueInstant(now);

    Issuer issuer = issuerBuilder.buildObject();
    issuer.setValue("urn:example.org:issuer");
    assertion.setIssuer(issuer);

    AuthnStatement authnStmt = authnStatementBuilder.buildObject();
    authnStmt.setAuthnInstant(now);
    assertion.getAuthnStatements().add(authnStmt);
    Signature signature = signatureBuilder.buildObject();
    BasicCredential basicCredential = new BasicCredential();
    basicCredential.setPrivateKey(saml.getRSAPrivateKey());
    signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
    signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA);
    signature.setSigningCredential(basicCredential);
    assertion.setSignature(signature);
    MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
    Marshaller marshaller = marshallerFactory.getMarshaller(assertion);
    marshaller.marshall(assertion);
    Signer.signObject(signature);

    try {
      Element authDOM = marshaller.marshall(assertion);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      System.out.println(messageXML);
    } catch (MarshallingException e) {
      throw new RuntimeException("创建Response错误:" + e.getMessage());
    }
  }

  protected XMLObject unmarshallElementString(String authReauest) {
    try {
      BasicParserPool parser;
      parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      Document doc = (Document) parser.parse(new ByteArrayInputStream(authReauest.getBytes()));
      Element samlElement = (Element) doc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
      return unmarshaller.unmarshall(samlElement);
    } catch (XMLParserException e) {
      throw new RuntimeException(e);
    } catch (UnmarshallingException e) {
      throw new RuntimeException(e);
    }
  }
}