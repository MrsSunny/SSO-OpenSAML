package org.sms.opensaml.service.impl;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.List;

import javax.crypto.SecretKey;
import javax.xml.namespace.QName;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeQuery;
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
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.SignableXMLObject;
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
import org.sms.core.id.UUIDFactory;
import org.sms.opensaml.service.SamlService;
import org.sms.project.helper.CertificateHelper;
import org.sms.project.user.entity.User;
import org.sms.project.util.GZipUtil;
import org.sms.project.util.HttpUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service("samlService")
public class SamlServiceImpl implements SamlService {

  private Logger logger = LoggerFactory.getLogger(SamlServiceImpl.class.getName());

  protected static final XMLObjectBuilderFactory builderFactory;

  static {
    try {
      DefaultBootstrap.bootstrap();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    Security.addProvider(new BouncyCastleProvider());
    builderFactory = Configuration.getBuilderFactory();
  }

  public String buildXMLObjectToString(XMLObject xmlObject) {
    Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(xmlObject);
    Element authDOM;
    try {
      authDOM = marshaller.marshall(xmlObject);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      String samlRequest = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlRequest.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      throw new RuntimeException(e);
    }
  }

  public XMLObject buildStringToXMLObject(String xmlObjectString) {
    try {
      BasicParserPool parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      byte[] decryptArray = Base64.decode(xmlObjectString);
      String xmlString = GZipUtil.gunzip(new String(decryptArray));
      Document doc = (Document) parser.parse(new ByteArrayInputStream(xmlString.getBytes()));
      Element samlElement = (Element) doc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
      return unmarshaller.unmarshall(samlElement);
    } catch (XMLParserException e) {
      throw new RuntimeException(e);
    } catch (UnmarshallingException e) {
      throw new RuntimeException(e);
    }
  }

  public AuthnRequest buildAuthnRequest(String ticket, String setAssertionConsumerServiceURL) {
    NameID nameid = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameid.setFormat(NameID.UNSPECIFIED);
    nameid.setValue(ticket);
    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    subject.setNameID(nameid);
    Audience audience = (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
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
    request.setAssertionConsumerServiceURL(setAssertionConsumerServiceURL);
    request.setAttributeConsumingServiceIndex(0);
    request.setProviderName("IDP Provider");
    request.setID("_" + UUIDFactory.INSTANCE.getUUID());
    request.setVersion(SAMLVersion.VERSION_20);
    request.setIssueInstant(new DateTime(2005, 1, 31, 12, 0, 0, 0, ISOChronology.getInstanceUTC()));
    request.setDestination(SysConstants.LOCALDOMAIN);
    request.setConsent("urn:oasis:names:tc:SAML:2.0:consent:obtained");
    Issuer rIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    rIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    rIssuer.setValue(SysConstants.LOCALDOMAIN);
    request.setIssuer(rIssuer);
    return request;
  }

  public Response buildResponse(String requestId) {
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
    Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
    conditions.setNotBefore(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    conditions.setNotOnOrAfter(new DateTime(2006, 1, 26, 13, 45, 5, 0, ISOChronology.getInstanceUTC()));
    response.setStatus(status);
    response.setIssuer(rIssuer);
    status.setStatusCode(statusCode);
    return response;
  }

  public void addAttribute(Response response, User user) {
    Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
    AuthnStatement authnStatement = (AuthnStatement) buildXMLObject(AuthnStatement.DEFAULT_ELEMENT_NAME);
    authnStatement.setAuthnInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    AuthnContext authnContext = (AuthnContext) buildXMLObject(AuthnContext.DEFAULT_ELEMENT_NAME);
    AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
    classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
    authnContext.setAuthnContextClassRef(classRef);
    authnStatement.setAuthnContext(authnContext);
    assertion.setID(UUIDFactory.INSTANCE.getUUID());
    assertion.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    assertion.getAuthnStatements().add(authnStatement);
    Issuer aIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    aIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    aIssuer.setValue(SysConstants.LOCALDOMAIN);
    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    NameID nameID = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameID.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:emailAddress");
    nameID.setValue(user.getEmail());
    SubjectConfirmation subjectConfirmation = (SubjectConfirmation) buildXMLObject(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
    subjectConfirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");
    subject.setNameID(nameID);
    subject.getSubjectConfirmations().add(subjectConfirmation);
    assertion.setIssuer(aIssuer);
    assertion.setSubject(subject);
    AttributeStatement attribStatement = (AttributeStatement) buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
    Attribute idAttribute = buildStringAttribute("Id", user.getId() + "");
    Attribute emailAttribute = buildStringAttribute("Email", user.getEmail());
    attribStatement.getAttributes().add(idAttribute);
    attribStatement.getAttributes().add(emailAttribute);
    assertion.getAttributeStatements().add(attribStatement);
    this.signXMLObject(assertion);
    response.getAssertions().add(assertion);
  }

  @Override
  public void signXMLObject(SignableXMLObject signableXMLObject) {
    SignatureBuilder signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
    BasicCredential basicCredential = new BasicCredential();
    basicCredential.setPrivateKey(CertificateHelper.getRSAPrivateKey());
    Signature signature = signatureBuilder.buildObject();
    signature.setCanonicalizationAlgorithm(SysConstants.CANON_ALGORITHM);
    signature.setSignatureAlgorithm(SysConstants.SIGNATURE_METHOD);
    signature.setSigningCredential(basicCredential);
    signableXMLObject.setSignature(signature);
    MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
    Marshaller marshaller = marshallerFactory.getMarshaller(signableXMLObject);
    try {
      marshaller.marshall(signableXMLObject);
      Signer.signObject(signature);
    } catch (MarshallingException e) {
      e.printStackTrace();
    } catch (SignatureException e) {
      e.printStackTrace();
    }
  }

  public Attribute buildStringAttribute(String name, String value) {
    Attribute attribute = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
    attribute.setName(name);
    XMLObjectBuilder<?> stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
    XSString ldapAttribValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME,
        XSString.TYPE_NAME);
    ldapAttribValue.setValue(value);
    attribute.getAttributeValues().add(ldapAttribValue);
    return attribute;
  }

  public String buildArtifactResolve(Artifact artifact) {
    ArtifactResolve artifactResolve = (ArtifactResolve) buildXMLObject(ArtifactResolve.DEFAULT_ELEMENT_NAME);
    artifactResolve.setArtifact(artifact);
    return buildXMLObjectToString(artifactResolve);
  }

  public SSODescriptor buildSSODescriptor(String xmlFilePath, Class<?> descriptorType) {
    EntityDescriptor entityDescriptor = (EntityDescriptor) unmarshallElementWithXMLFile(xmlFilePath);
    if (descriptorType.getClass().getName().equals(IDPSSODescriptor.class.getName())) {
      return entityDescriptor.getIDPSSODescriptor("urn:oasis:names:tc:SAML:2.0:protocol");
    }
    return entityDescriptor.getSPSSODescriptor("urn:oasis:names:tc:SAML:2.0:protocol");
  }

  @Override
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

  @Override
  public XMLObject unmarshallElementWithXMLFile(String elementFile) {
    try {
      BasicParserPool parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      Document doc = parser.parse(SamlServiceImpl.class.getResourceAsStream(elementFile));
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
   * 
   * @param assertion
   * @param receiverCredential
   * @return
   */
  @Override
  public EncryptedAssertion encrypt(Assertion assertion, X509Credential receiverCredential) {
    Credential symmetricCredential;
    EncryptedAssertion encrypted = null;
    try {
      symmetricCredential = SecurityHelper.getSimpleCredential(SecurityHelper
          .generateSymmetricKey(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128));
      EncryptionParameters encParams = new EncryptionParameters();
      encParams.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
      encParams.setEncryptionCredential(symmetricCredential);
      KeyEncryptionParameters kek = new KeyEncryptionParameters();
      kek.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
      kek.setEncryptionCredential(receiverCredential);
      Encrypter encrypter = new Encrypter(encParams, kek);
      encrypter.setKeyPlacement(KeyPlacement.INLINE);
      encrypted = encrypter.encrypt(assertion);
    } catch (NoSuchAlgorithmException | KeyException e) {
      e.printStackTrace();
    } catch (EncryptionException e) {
      e.printStackTrace();
    }
    return encrypted;
  }

  /**
   * 解密断言
   * 
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   */
  @Override
  public Assertion decrypt(EncryptedAssertion enc, Credential credential, String federationMetadata) {
    KeyInfoCredentialResolver keyResolver = new StaticKeyInfoCredentialResolver(credential);
    EncryptedKey key = enc.getEncryptedData().getKeyInfo().getEncryptedKeys().get(0);
    Decrypter decrypter = new Decrypter(null, keyResolver, new InlineEncryptedKeyResolver());
    decrypter.setRootInNewDocument(true);
    SecretKey dkey;
    Assertion assertion = null;
    try {
      dkey = (SecretKey) decrypter.decryptKey(key, enc.getEncryptedData().getEncryptionMethod().getAlgorithm());
      Credential shared = SecurityHelper.getSimpleCredential(dkey);
      decrypter = new Decrypter(new StaticKeyInfoCredentialResolver(shared), null, null);
      decrypter.setRootInNewDocument(true);
      assertion = decrypter.decrypt(enc);
    } catch (DecryptionException e) {
      e.printStackTrace();
    }
    return assertion;
  }

  /**
   * 签名断言
   * 
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   */
  @Override
  public Signature signature() {
    SignatureBuilder signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
    BasicCredential basicCredential = new BasicCredential();
    Signature signature = signatureBuilder.buildObject();
    basicCredential.setPrivateKey(CertificateHelper.getRSAPrivateKey());
    signature.setCanonicalizationAlgorithm(SysConstants.CANON_ALGORITHM);
    signature.setSignatureAlgorithm(SysConstants.SIGNATURE_METHOD);
    return signature;
  }

  /**
   * 验签断言
   * 
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   */
  @Override
  public boolean validate(String base64Response) {
    SignableXMLObject signableXMLObject = (SignableXMLObject) buildStringToXMLObject(base64Response);
    BasicCredential basicCredential = new BasicCredential();
    basicCredential.setPublicKey(CertificateHelper.getRSAPublicKey());
    SignatureValidator signatureValidator = new SignatureValidator(basicCredential);
    Signature signature = signableXMLObject.getSignature();
    try {
      signatureValidator.validate(signature);
      return true;
    } catch (ValidationException e) {
      logger.debug("验证签名错误" + e.getMessage());
      return false;
    }
  }

  @Override
  public boolean validate(SignableXMLObject signableXMLObject) {
    BasicCredential basicCredential = new BasicCredential();
    basicCredential.setPublicKey(CertificateHelper.getRSAPublicKey());
    SignatureValidator signatureValidator = new SignatureValidator(basicCredential);
    Signature signature = signableXMLObject.getSignature();
    try {
      signatureValidator.validate(signature);
      return true;
    } catch (ValidationException e) {
      logger.debug("验证签名错误" + e.getMessage());
      return false;
    }
  }

  @Override
  public Artifact buildArtifact() {
    String artifactId = UUIDFactory.INSTANCE.getUUID();
    Artifact artifact = (Artifact) buildXMLObject(Artifact.DEFAULT_ELEMENT_NAME);
    artifact.setArtifact(artifactId);
    return artifact;
  }

  @Override
  public ArtifactResolve buildArtifactResolve() {
    String artifactResolveId = UUIDFactory.INSTANCE.getUUID();
    ArtifactResolve artifactResolve = (ArtifactResolve) buildXMLObject(ArtifactResolve.DEFAULT_ELEMENT_NAME);
    artifactResolve.setID(artifactResolveId);
    Issuer aIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    aIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    aIssuer.setValue(SysConstants.LOCALDOMAIN_SAML2_SERVICE);
    artifactResolve.setIssuer(aIssuer);
    artifactResolve.setVersion(SAMLVersion.VERSION_20);
    artifactResolve.setDestination(SysConstants.SP_ARTIFACT_RESOLUTION_SERVICE);
    artifactResolve.setIssueInstant(new DateTime(2005, 1, 31, 12, 0, 0, 0, ISOChronology.getInstanceUTC()));
    return artifactResolve;
  }

  @Override
  public ArtifactResponse buildArtifactResponse() {
    String artifactResponseId = UUIDFactory.INSTANCE.getUUID();
    ArtifactResponse artifactResponse = (ArtifactResponse) buildXMLObject(ArtifactResponse.DEFAULT_ELEMENT_NAME);
    artifactResponse.setID(artifactResponseId);
    artifactResponse.setVersion(SAMLVersion.VERSION_20);
    artifactResponse.setIssueInstant(new DateTime(2005, 1, 31, 12, 0, 0, 0, ISOChronology.getInstanceUTC()));
    return artifactResponse;
  }

  @Override
  public AttributeQuery buildAttributeQuery() {
    AttributeQuery attributeQuery = (AttributeQuery) buildXMLObject(AttributeQuery.DEFAULT_ELEMENT_NAME);
    return attributeQuery;
  }

  @Override
  public Status getStatusCode(boolean success) {
    Status status = (Status) buildXMLObject(Status.DEFAULT_ELEMENT_NAME);
    StatusCode statusCode = (StatusCode) buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
    statusCode.setValue(success ? StatusCode.SUCCESS_URI : StatusCode.AUTHN_FAILED_URI);
    status.setStatusCode(statusCode);
    return status;
  }

  @Override
  public User getUserByAssertion(String spArtifact) {
    if (null == spArtifact) {
      logger.debug("无法获取IDP端传过来的Artifact");
      return null;
    }
    ArtifactResolve artifactResolve = buildArtifactResolve();
    Artifact artifact = (Artifact) buildStringToXMLObject(spArtifact);
    artifactResolve.setArtifact(artifact);
    signXMLObject(artifactResolve);

    String requestStr = buildXMLObjectToString(artifactResolve);
    String postResult = null;
    try {
      postResult = HttpUtil.doPost(SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE, requestStr);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("访问IDP的" + SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE + "服务错误");
      return null;
    }
    if (null == postResult) {
      logger.debug("从" + SysConstants.IDP_ARTIFACT_RESOLUTION_SERVICE + "服务获取的数据为空，请检查IDP端数据格式");
      return null;
    }
    ArtifactResponse artifactResponse = (ArtifactResponse) buildStringToXMLObject(postResult);
    SAMLObject samlObject = artifactResponse.getMessage();
    if (null == samlObject) {
      logger.debug("无法获取Response信息");
      return null;
    }
    Response samlResponse = (Response) samlObject;
    List<Assertion> assertions = samlResponse.getAssertions();
    if (null == assertions || assertions.size() == 0) {
      logger.debug("无法获取断言，请重新发起请求！！！");
      return null;
    }
    Assertion assertion = samlResponse.getAssertions().get(0);
    if (assertion == null) {
      return null;
    }
    /**
     * 验证签名
     */
    boolean signSuccess = validate(assertion);
    if (!signSuccess) {
      logger.debug("验证签名错误");
      return null;
    }
    List<AttributeStatement> arrtibuteStatements = assertion.getAttributeStatements();
    if (null == arrtibuteStatements || arrtibuteStatements.size() == 0) {
      logger.debug("无法获取属性列表，请重新发起请求");
      return null;
    }
    AttributeStatement attributeStatement = assertion.getAttributeStatements().get(0);
    List<Attribute> list = attributeStatement.getAttributes();
    if (null == list) {
      logger.debug("无法获取属性列表IDP端错误");
      return null;
    }
    User user = new User();
    list.forEach(pereAttribute -> {
      String name = pereAttribute.getName();
      XSString value = (XSString) pereAttribute.getAttributeValues().get(0);
      String valueString = value.getValue();
      if (name.endsWith("Name")) {
        user.setName(valueString);
      } else if (name.equals("Id")) {
        user.setId(Long.parseLong(valueString));
      } else if (name.equals("Email")) {
        user.setEmail(valueString);
      }
    });
    return user;
  }
}