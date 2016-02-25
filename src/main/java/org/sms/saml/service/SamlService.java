package org.sms.saml.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
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
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.provider.HTTPMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.validation.ValidationException;
import org.sms.saml.core.SamlUtils;
import org.sms.saml.dao.SamlDao;
import org.sms.util.GZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service
public class SamlService {

  @Autowired
  private SamlDao samlDao;

  protected static XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();;

  static {
    try {
      DefaultBootstrap.bootstrap();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    Security.addProvider(new BouncyCastleProvider());
  }

  public String buildRequest() {
    NameID nameid = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameid.setFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress");
    nameid.setValue("j.doe@company.com");

    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    subject.setNameID(nameid);

    Audience audience = (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
    audience.setAudienceURI("urn:foo:sp.example.org");

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

    request.setForceAuthn(XSBooleanValue.valueOf("true"));
    request.setAssertionConsumerServiceURL("http://www.example.com/");
    request.setAttributeConsumingServiceIndex(0);
    request.setProviderName("SomeProvider");
    request.setID("abe567de6");
    request.setVersion(SAMLVersion.VERSION_20);
    request.setIssueInstant(new DateTime(2005, 1, 31, 12, 0, 0, 0, ISOChronology.getInstanceUTC()));
    request.setDestination("http://www.example.com/");
    request.setConsent("urn:oasis:names:tc:SAML:2.0:consent:obtained");

    Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(request);
    Element authDOM;
    try {
      authDOM = marshaller.marshall(request);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      System.out.println(messageXML);
      String samlRequest = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlRequest.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 根据登录情况返回AuthSamlResponse
   * 
   * @return
   */
  public String buildResponse() {
    Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
    String responseID = UUID.randomUUID().toString().replace("-", "");
    response.setID(responseID);
    response.setInResponseTo("_abcdef123456");
    response.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));

    Issuer rIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    rIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    rIssuer.setValue("https://idp.example.org");

    Status status = (Status) buildXMLObject(Status.DEFAULT_ELEMENT_NAME);
    StatusCode statusCode = (StatusCode) buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
    statusCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Success");

    Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
    assertion.setID("_a75adf55-01d7-40cc-929f-dbd8372ebdfc");
    assertion.setIssueInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));

    Issuer aIssuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    aIssuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    aIssuer.setValue("https://idp.example.org");

    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    NameID nameID = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameID.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
    nameID.setValue("_820d2843-2342-8236-ad28-8ac94fb3e6a1");

    SubjectConfirmation subjectConfirmation = (SubjectConfirmation) buildXMLObject(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
    subjectConfirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");

    Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
    conditions.setNotBefore(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));
    conditions.setNotOnOrAfter(new DateTime(2006, 1, 26, 13, 45, 5, 0, ISOChronology.getInstanceUTC()));

    AudienceRestriction audienceRestriction = (AudienceRestriction) buildXMLObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);
    Audience audience = (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
    audience.setAudienceURI("https://sp.example.org");

    AuthnStatement authnStatement = (AuthnStatement) buildXMLObject(AuthnStatement.DEFAULT_ELEMENT_NAME);
    authnStatement.setAuthnInstant(new DateTime(2006, 1, 26, 13, 35, 5, 0, ISOChronology.getInstanceUTC()));

    AuthnContext authnContext = (AuthnContext) buildXMLObject(AuthnContext.DEFAULT_ELEMENT_NAME);
    AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
    classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");

    AttributeStatement attribStatement = (AttributeStatement) buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
    XMLObjectBuilder<?> stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
    Attribute fooAttrib = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
    fooAttrib.setFriendlyName("fooAttrib");
    fooAttrib.setName("urn:foo:attrib");
    fooAttrib.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
    XSString fooAttribValue = null;
    fooAttribValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
    fooAttribValue.setValue("SomeValue");
    fooAttrib.getAttributeValues().add(fooAttribValue);
    fooAttribValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
    fooAttribValue.setValue("SomeOtherValue");
    fooAttrib.getAttributeValues().add(fooAttribValue);

    Attribute ldapAttrib = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
    ldapAttrib.setFriendlyName("eduPersonPrincipalName");
    ldapAttrib.setName("urn:oid:1.3.6.1.4.1.5923.1.1.1.6");
    ldapAttrib.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
    XSString ldapAttribValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
    ldapAttribValue.setValue("j.doe@idp.example.org");
    ldapAttrib.getAttributeValues().add(ldapAttribValue);
    response.setIssuer(rIssuer);
    status.setStatusCode(statusCode);
    response.setStatus(status);
    response.getAssertions().add(assertion);
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
    attribStatement.getAttributes().add(fooAttrib);
    attribStatement.getAttributes().add(ldapAttrib);
    assertion.getAttributeStatements().add(attribStatement);

    Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(response);
    Element authDOM;
    try {
      authDOM = marshaller.marshall(response);
      StringWriter rspWrt = new StringWriter();
      XMLHelper.writeNode(authDOM, rspWrt);
      String messageXML = rspWrt.toString();
      String samlResponse = GZipUtil.gzip(messageXML);
      return Base64.encodeBytes(samlResponse.getBytes(), Base64.DONT_BREAK_LINES);
    } catch (MarshallingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String buildArtifact(String artifactId) {
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
      e.printStackTrace();
    }
    return null;
  }

  public void buildIDPSSODescriptor() {
    String singleElementFile = "";
    IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(singleElementFile);
    System.out.println(descriptor);
  }

  public void buildSPSSODescriptor() {
    String singleElementFile = "";
    SPSSODescriptor descriptor = (SPSSODescriptor) unmarshallElement(singleElementFile);
    System.out.println(descriptor);
  }

  public String decodeSAMLResponse(String samlResponse) {
    byte[] decodedBytes = Base64.decode(samlResponse);
    return new String(decodedBytes);
  }

  public XMLObject buildXMLObject(QName objectQName) {
    XMLObjectBuilder<?> builder = Configuration.getBuilderFactory().getBuilder(objectQName);
    return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }

  public String validateSAMLResponse(String samlResponse, String samlCert) throws Exception {
    String decodedString = "";
    try {
      decodedString = decodeSAMLResponse(samlResponse);
      InputStream inputStream = new ByteArrayInputStream(decodedString.getBytes("UTF-8"));
      // Parse XML
      BasicParserPool parserPoolManager = new BasicParserPool();
      parserPoolManager.setNamespaceAware(true);
      parserPoolManager.setIgnoreElementContentWhitespace(true);
      Document document = parserPoolManager.parse(inputStream);
      Element metadataRoot = document.getDocumentElement();
      QName qName = new QName(metadataRoot.getNamespaceURI(), metadataRoot.getLocalName(), metadataRoot.getPrefix());
      // Unmarshall document
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(qName);
      Response response = (Response) unmarshaller.unmarshall(metadataRoot);
      // Issuer issuer = response.getIssuer();
      java.security.cert.X509Certificate jX509Cert = SamlUtils.parsePemCertificate(samlCert);
      if (null == jX509Cert) {
        return "";
      }

      PublicKey publicCert = jX509Cert.getPublicKey();
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicCert.getEncoded());

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

      // Setup validation
      BasicX509Credential publicCredential = new BasicX509Credential();
      publicCredential.setPublicKey(publicKey);
      SignatureValidator signatureValidator = new SignatureValidator(publicCredential);
      Signature signature = response.getSignature();
      // Validate
      try {
        signatureValidator.validate(signature);
      } catch (ValidationException e) {
        throw e;
      }

      // Get decryption key
      RSAPrivateKey privateKey = null;
      BasicX509Credential decryptionCredential = new BasicX509Credential();
      decryptionCredential.setPrivateKey(privateKey);
      StaticKeyInfoCredentialResolver skicr = new StaticKeyInfoCredentialResolver(decryptionCredential);

      // Decrypt assertion
      Decrypter decrypter = new Decrypter(null, skicr, new InlineEncryptedKeyResolver());
      if (response.getEncryptedAssertions().isEmpty()) {
      } else {
        Assertion decryptedAssertion;
        try {
          decryptedAssertion = decrypter.decrypt(response.getEncryptedAssertions().get(0));
        } catch (DecryptionException e) {
          throw e;
        }
        List<AttributeStatement> attributeStatements = decryptedAssertion.getAttributeStatements();
        for (int i = 0; i < attributeStatements.size(); i++) {
          List<Attribute> attributes = attributeStatements.get(i).getAttributes();
          for (int x = 0; x < attributes.size(); x++) {
            List<XMLObject> attributeValues = attributes.get(x).getAttributeValues();
            for (int y = 0; y < attributeValues.size(); y++) {
            }
          }
        }
      }
    } catch (Exception ex) {
      throw ex;
    }
    return decodedString;
  }

  public String loadSigningKeyFromMetaData(String metadataUri, String a) {
    try {
      @SuppressWarnings("deprecation")
      HTTPMetadataProvider mdp = new HTTPMetadataProvider(metadataUri, 5000);
      mdp.setRequireValidMetadata(true);
      mdp.setParserPool(new BasicParserPool());
      mdp.initialize();
      EntityDescriptor idpEntityDescriptor = mdp.getEntityDescriptor(metadataUri);

      for (KeyDescriptor key : idpEntityDescriptor.getIDPSSODescriptor(SAMLConstants.SAML20P_NS).getKeyDescriptors()) {
        X509Data x509 = key.getKeyInfo().getX509Datas().get(0);
        org.opensaml.xml.signature.X509Certificate x509Cert = x509.getX509Certificates().get(0);
        return SamlUtils.convertCertToPemFormat(x509Cert.getValue());
      }
    } catch (MetadataProviderException e) {
    }
    return null;
  }

  protected static XMLObject unmarshallElement(String authReauest) {
    try {
      BasicParserPool parser;
      parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      Document doc = (Document) parser.parse(new ByteArrayInputStream(authReauest.getBytes()));
      Element samlElement = (Element) doc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
      return unmarshaller.unmarshall(samlElement);
    } catch (XMLParserException e) {
      e.printStackTrace();
    } catch (UnmarshallingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public AuthnRequest getAuthRequest(String authRequest) {
    AuthnRequest request = null;
    try {
      byte[] check = Base64.decode(authRequest);
      String gunZip = GZipUtil.gunzip(new String(check));
      request = (AuthnRequest) unmarshallElement(gunZip);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return request;
  }

  public String consumerServiceURL(AuthnRequest request) {
    if (null == request) return null;
    return request.getAssertionConsumerServiceURL();
  }

  public boolean checkUrl(String url) {
    return samlDao.checkUrl(url);
  }
}