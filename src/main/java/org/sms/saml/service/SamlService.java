package org.sms.saml.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

import javax.crypto.SecretKey;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.saml2.encryption.Encrypter;
import org.opensaml.saml2.encryption.Encrypter.KeyPlacement;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.SysConstants;
import org.sms.component.idfactory.UUIDFactory;
import org.sms.saml.dao.SamlDao;
import org.sms.util.GZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Service
public class SamlService {
  
  private Logger logger = LoggerFactory.getLogger(SamlService.class.getName());

  @Autowired
  private SamlDao samlDao;

  protected static XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

  static {
    try {
      DefaultBootstrap.bootstrap();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    Security.addProvider(new BouncyCastleProvider());
  }

  public String buildRequest() {
    AuthnRequest request = getRequest();
    Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(request);
    Element authDOM;
    try {
      authDOM = marshaller.marshall(request);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      logger.debug("Request：" + messageXML);
      System.out.println(messageXML);
      String samlRequest = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlRequest.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      throw new RuntimeException(e);
    }
  }
  
  public AuthnRequest getRequest() {
    NameID nameid = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameid.setFormat(NameID.UNSPECIFIED);
    nameid.setValue("sunny@soaer.com");
    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    subject.setNameID(nameid);
    Audience audience= (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
    audience.setAudienceURI(SysConstants.LOCALDOMAIN);
    AudienceRestriction ar = (AudienceRestriction) buildXMLObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);
    ar.getAudiences().add(audience);
    Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
    conditions.getAudienceRestrictions().add(ar);
    AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
    classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
    RequestedAuthnContext rac = (RequestedAuthnContext) buildXMLObject(RequestedAuthnContext.DEFAULT_ELEMENT_NAME);
    rac.getAuthnContextClassRefs().add(classRef);
    AuthnRequest request = (AuthnRequest) buildXMLObject(AuthnRequest.DEFAULT_ELEMENT_NAME);
    request.setSubject(subject);
    request.setConditions(conditions);
    request.setRequestedAuthnContext(rac);
    request.setForceAuthn(false);
    request.setAssertionConsumerServiceURL(SysConstants.LOCALDOMAIN);
    request.setAttributeConsumingServiceIndex(0);
    request.setProviderName("IDP Provider");
    request.setID("_" + UUIDFactory.INSTANCE.getUUID());
    request.setVersion(SAMLVersion.VERSION_20);
    request.setIssueInstant(new DateTime(2005, 1, 31, 12, 0, 0, 0, ISOChronology.getInstanceUTC()));
    request.setDestination(SysConstants.LOCALDOMAIN);
    request.setConsent("urn:oasis:names:tc:SAML:2.0:consent:obtained");
    return request;
  }

  /**
   * 根据登录情况返回AuthSamlResponse
   * @return
   * @throws ConfigurationException 
   */
  public String buildResponse(String requestId, String audienceURI) throws ConfigurationException {
    Response response = getResponse(requestId, audienceURI);
    Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(response);
    try {
      Element authDOM = marshaller.marshall(response);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      logger.debug("Response：" + messageXML);
      System.out.println(messageXML);
      String samlResponse = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlResponse.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      throw new RuntimeException("创建Response错误:" + e.getMessage());
    }
  }
  
  public String buildResponse(Response response) {
    Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(response);
    try {
      Element authDOM = marshaller.marshall(response);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      System.out.println(messageXML);
      String samlResponse = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlResponse.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      throw new RuntimeException("创建Response错误:" + e.getMessage());
    }
  }

  public Response getResponse(String requestId, String audienceURI) throws ConfigurationException {
    Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
    response.setID(UUIDFactory.INSTANCE.getUUID());
    response.setInResponseTo(requestId);
    response.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    Issuer rIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    rIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    rIssuer.setValue(SysConstants.LOCALDOMAIN);
    Status status = (Status) buildXMLObject(Status.DEFAULT_ELEMENT_NAME);
    StatusCode statusCode = (StatusCode) buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
    statusCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Success");
    Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
    assertion.setID(UUIDFactory.INSTANCE.getUUID());
    assertion.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    Issuer aIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    aIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    aIssuer.setValue(SysConstants.LOCALDOMAIN);
    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    NameID nameID = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameID.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
    nameID.setValue("_" + UUIDFactory.INSTANCE.getUUID());
    SubjectConfirmation subjectConfirmation = (SubjectConfirmation) buildXMLObject(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
    subjectConfirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");
    Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
    conditions.setNotBefore(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    conditions.setNotOnOrAfter(new DateTime(2006, 1, 26, 13, 45, 5, 0, ISOChronology.getInstanceUTC()));
    AudienceRestriction audienceRestriction = (AudienceRestriction) buildXMLObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);
    Audience audience = (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
    audience.setAudienceURI(audienceURI);
    AuthnStatement authnStatement = (AuthnStatement) buildXMLObject(AuthnStatement.DEFAULT_ELEMENT_NAME);
    authnStatement.setAuthnInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    AuthnContext authnContext = (AuthnContext) buildXMLObject(AuthnContext.DEFAULT_ELEMENT_NAME);
    AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
    classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
    AttributeStatement attribStatement = (AttributeStatement) buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
    Attribute nameAttribute = buildStringAttribute("Name", "Sunny");
    Attribute idAttribute = buildStringAttribute("Id", "10000020000010003");
    response.setStatus(status);
    response.setIssuer(rIssuer);
    status.setStatusCode(statusCode);
    
    /**
     * 添加断言
     */
    assertion.setIssuer(aIssuer);
    subject.setNameID(nameID);
    subject.getSubjectConfirmations().add(subjectConfirmation);
    assertion.setSubject(subject);
    audienceRestriction.getAudiences().add(audience);
    conditions.getAudienceRestrictions().add(audienceRestriction);
    assertion.setConditions(conditions);
    authnContext.setAuthnContextClassRef(classRef);
    authnStatement.setAuthnContext(authnContext);
    assertion.getAuthnStatements().add(authnStatement);
    attribStatement.getAttributes().add(nameAttribute);
    attribStatement.getAttributes().add(idAttribute);
    assertion.getAttributeStatements().add(attribStatement);
    SignatureBuilder signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
    BasicCredential basicCredential = new BasicCredential();
    Signature signature = signatureBuilder.buildObject();
    basicCredential.setPrivateKey(getRSAPrivateKey());
    signature.setCanonicalizationAlgorithm(SysConstants.CANON_ALGORITHM);
    signature.setSignatureAlgorithm(SysConstants.SIGNATURE_METHOD);
    signature.setSigningCredential(basicCredential);
    assertion.setSignature(signature);
    Marshaller marshaller = null;
    MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
    marshaller = marshallerFactory.getMarshaller(assertion);
    try {
      marshaller.marshall(assertion);
    } catch (MarshallingException e) {
      e.printStackTrace();
    }
    try {
      Signer.signObject(signature);
    } catch (SignatureException e) {
      e.printStackTrace();
    }
    response.getAssertions().add(assertion);
    return response;
  }
  
  public Attribute buildStringAttribute(String name, String value) throws ConfigurationException {
    Attribute attribute = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
    attribute.setName(name);
    XMLObjectBuilder<?> stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
    XSString ldapAttribValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
    ldapAttribValue.setValue(value);
    attribute.getAttributeValues().add(ldapAttribValue);
    return attribute;
  }

  public String buildArtifact() {
    String artifactId = UUIDFactory.INSTANCE.getUUID();
    Artifact artifact = (Artifact) buildXMLObject(Artifact.DEFAULT_ELEMENT_NAME);
    artifact.setArtifact(artifactId);
    Marshaller marshaller = org.opensaml.Configuration.getMarshallerFactory().getMarshaller(artifact);
    Element authDOM;
    try {
      authDOM = marshaller.marshall(artifact);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      String samlArtifact = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlArtifact.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      throw new RuntimeException("创建Artifact错误:" + e.getMessage());
    }
  }

  public SSODescriptor buildSSODescriptor(String xmlFilePath, Class<?> descriptorType) {
    EntityDescriptor entityDescriptor = (EntityDescriptor) unmarshallElementWithXMLFile(xmlFilePath);
    if (descriptorType.getClass().getName().equals(IDPSSODescriptor.class.getName())) {
      return entityDescriptor.getIDPSSODescriptor("urn:oasis:names:tc:SAML:2.0:protocol");
    }
    return entityDescriptor.getSPSSODescriptor("urn:oasis:names:tc:SAML:2.0:protocol");
  }

  public PublicKey getRSAPublicKey() {
    SSODescriptor _SPSSODescriptor = buildSSODescriptor(SysConstants.SPSSPXMLFILE, SPSSODescriptor.class);
    List<KeyDescriptor> keyDescriptors = _SPSSODescriptor.getKeyDescriptors();
    KeyDescriptor keyDescriptor = keyDescriptors.get(0);
    KeyInfo keyInfo = keyDescriptor.getKeyInfo();
    List<X509Data> x509Datas = keyInfo.getX509Datas();
    List<X509Certificate> x509Certificates = x509Datas.get(0).getX509Certificates();
    X509Certificate x509Certificate = x509Certificates.get(0);
    String _x509Value = x509Certificate.getValue();
    try {
      java.security.cert.X509Certificate cert = SecurityHelper.buildJavaX509Cert(_x509Value);
      return cert.getPublicKey();
    } catch (CertificateException e) {
      throw new RuntimeException("获取公钥错误 :" + e.getMessage());
    }
  }

  public RSAPrivateKey getRSAPrivateKey() {
    SSODescriptor _IDPSSODescriptor = buildSSODescriptor(SysConstants.IDPSSPXMLFILE, IDPSSODescriptor.class);
    List<KeyDescriptor> keyDescriptors = _IDPSSODescriptor.getKeyDescriptors();
    KeyDescriptor keyDescriptor = keyDescriptors.get(0);
    KeyInfo keyInfo = keyDescriptor.getKeyInfo();
    List<X509Data> x509Datas = keyInfo.getX509Datas();
    List<X509Certificate> x509Certificates = x509Datas.get(0).getX509Certificates();
    X509Certificate x509Certificate = x509Certificates.get(0);
    try {
      return SecurityHelper.buildJavaRSAPrivateKey(x509Certificate.getValue());
    } catch (KeyException e) {
      throw new RuntimeException("获取私钥错误:" + e.getMessage());
    }
  }

  public X509Certificate getX509Certificate(String xmlFilePath) {
    SSODescriptor _SPSSODescriptor = buildSSODescriptor(xmlFilePath, SPSSODescriptor.class);
    List<KeyDescriptor> keyDescriptors = _SPSSODescriptor.getKeyDescriptors();
    KeyDescriptor keyDescriptor = keyDescriptors.get(0);
    KeyInfo keyInfo = keyDescriptor.getKeyInfo();
    List<X509Data> x509Datas = keyInfo.getX509Datas();
    List<X509Certificate> x509Certificates = x509Datas.get(0).getX509Certificates();
    X509Certificate x509Certificate = x509Certificates.get(0);
    return x509Certificate;
  }

  public String decodeSAMLResponse(String samlResponse) {
    byte[] decodedBytes = Base64.decode(samlResponse);
    return new String(decodedBytes);
  }

  public XMLObject buildXMLObject(QName objectQName) {
    XMLObjectBuilder<?> builder = Configuration.getBuilderFactory().getBuilder(objectQName);
    return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }

  protected XMLObject unmarshallElement(String authReauest) {
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

  protected XMLObject unmarshallElementWithXMLFile(String elementFile) {
    try {
      BasicParserPool parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      Document doc = parser.parse(SamlService.class.getResourceAsStream(elementFile));
      Element samlElement = doc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
      return unmarshaller.unmarshall(samlElement);
    } catch (XMLParserException e) {
      throw new RuntimeException(e);
    } catch (UnmarshallingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 加密断言
   * @param assertion
   * @param receiverCredential
   * @return
   * @throws EncryptionException
   * @throws NoSuchAlgorithmException
   * @throws KeyException
   */
  public EncryptedAssertion encrypt(Assertion assertion, X509Credential receiverCredential) throws EncryptionException, NoSuchAlgorithmException, KeyException {
    Credential symmetricCredential = SecurityHelper.getSimpleCredential(SecurityHelper.generateSymmetricKey(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128));
    EncryptionParameters encParams = new EncryptionParameters();
    encParams.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
    encParams.setEncryptionCredential(symmetricCredential);
    KeyEncryptionParameters kek = new KeyEncryptionParameters();
    kek.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
    kek.setEncryptionCredential(receiverCredential);
    Encrypter encrypter = new Encrypter(encParams, kek);
    encrypter.setKeyPlacement(KeyPlacement.INLINE);
    EncryptedAssertion encrypted = encrypter.encrypt(assertion);
    return encrypted;
  }

  /**
   * 解密断言
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   * @throws DecryptionException
   * @throws ValidationException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws MetadataProviderException
   * @throws SecurityException
   */
  public Assertion decrypt(EncryptedAssertion enc, Credential credential, String federationMetadata) throws DecryptionException, ValidationException,
      ParserConfigurationException, SAXException, IOException, MetadataProviderException, SecurityException {
    KeyInfoCredentialResolver keyResolver = new StaticKeyInfoCredentialResolver(credential);
    EncryptedKey key = enc.getEncryptedData().getKeyInfo().getEncryptedKeys().get(0);
    Decrypter decrypter = new Decrypter(null, keyResolver, new InlineEncryptedKeyResolver());
    decrypter.setRootInNewDocument(true);
    SecretKey dkey = (SecretKey) decrypter.decryptKey(key, enc.getEncryptedData().getEncryptionMethod().getAlgorithm());
    Credential shared = SecurityHelper.getSimpleCredential(dkey);
    decrypter = new Decrypter(new StaticKeyInfoCredentialResolver(shared), null, null);
    decrypter.setRootInNewDocument(true);
    Assertion assertion = decrypter.decrypt(enc);
    return assertion;
  }

  /**
   * 签名断言
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   * @throws DecryptionException
   * @throws ValidationException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws MetadataProviderException
   * @throws SecurityException
   */
  public Signature signature() {
    SignatureBuilder signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
    BasicCredential basicCredential = new BasicCredential();
    Signature signature = signatureBuilder.buildObject();
    basicCredential.setPrivateKey(getRSAPrivateKey());
    signature.setCanonicalizationAlgorithm(SysConstants.CANON_ALGORITHM);
    signature.setSignatureAlgorithm(SysConstants.SIGNATURE_METHOD);
    return signature;
  }

  /**
   * 验签断言
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   * @throws DecryptionException
   * @throws ValidationException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws MetadataProviderException
   * @throws SecurityException
   */
  public boolean validate(String base64Response) {
    Response response = null;
    try {
      byte[] check = Base64.decode(base64Response);
      String gunZip = GZipUtil.gunzip(new String(check));
      response = (Response) unmarshallElement(gunZip);
    } catch (Exception e) {
      e.printStackTrace();
    }
    BasicCredential basicCredential = new BasicCredential();
    basicCredential.setPublicKey(getRSAPublicKey());
    SignatureValidator signatureValidator = new SignatureValidator(basicCredential);
    Assertion fristAssertion = response.getAssertions().get(0);
    Signature signature = fristAssertion.getSignature();
    try {
      signatureValidator.validate(signature);
      return true;
    } catch (ValidationException e) {
      logger.debug("验证签名错误" + e.getMessage());
      return false;
    }
  }
}