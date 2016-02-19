package org.sms.saml.core;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

public final class SamlGenerator {

  // Configuration parameters, currently hardcoded
  private static String KEY_ENCRYPTION_METHOD = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
  private static String CANON_ALGORITHM = "http://www.w3.org/2001/10/xml-exc-c14n#";
  private static String SIGNATURE_METHOD = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
  private static String SUBJCONF_METHOD = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
  private static String SAML_NAMEID_FORMAT = NameIDType.UNSPECIFIED;
  private static int SAML_DRIFT = 15; // skew the time back
  private static int SAML_VALIDITY = 300; // time to live for Saml

  // Commonly used encodings
  public static final String ENC_ASCII = "US-ASCII";
  public static final String ENC_UTF8 = "UTF-8";
  public static final String ENC_DEFAULT = ENC_UTF8;

  // Commonly used charsets (for the encodings above)
  public static final Charset CHARSET_ASCII = Charset.forName(ENC_ASCII);
  public static final Charset CHARSET_UTF8 = Charset.forName(ENC_UTF8);
  public static final Charset CHARSET_DEFAULT = CHARSET_UTF8;

  // Check this system has support for the basic charsets we rely on.
  static {
    if (CHARSET_ASCII == null) {
      throw new RuntimeException(" This system doesn't have support for " + ENC_ASCII + " charset!");
    } else if (CHARSET_UTF8 == null) {
      throw new RuntimeException(" This system doesn't have support for " + ENC_UTF8 + " charset!");
    }
  }

  private final BasicCredential basicCredential;
  private final AssertionBuilder assertionBuilder;
  private final XMLObjectBuilderFactory builderFactory;
  private XMLObjectBuilder<?> stringBuilder;
  private final SubjectConfirmationBuilder subjConfBuilder;
  private final NameIDBuilder nameIDBuilder;
  private final IssuerBuilder issuerBuilder;
  private final AuthnStatementBuilder authnStatementBuilder;
  private final SignatureBuilder signatureBuilder;
  private SecureRandomIdentifierGenerator idGenerator;
  private final MarshallerFactory marshallerFactory;
  private final List<KeyEncryptionParameters> kekParamsList;
  private String issuerName = null;
  private String idpMetaDataURL = null;

  /**
   * Setup generator for signing assertions and encrypting attributes.
   *
   * @param signingKey
   *          Key used for signing the assertion.
   * @param recipientCert
   *          Certificate used for encrypting attributes inside assertion.
   * @throws SamlException
   */
  public SamlGenerator(KeyPair signingKey, X509Certificate recipientCert) throws SamlException {

    basicCredential = new BasicCredential();
    basicCredential.setPrivateKey(signingKey.getPrivate());
    basicCredential.setPublicKey(signingKey.getPublic());
    builderFactory = Configuration.getBuilderFactory();

    assertionBuilder = (AssertionBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
    issuerBuilder = (IssuerBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
    authnStatementBuilder = (AuthnStatementBuilder) builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
    signatureBuilder = (SignatureBuilder) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
    subjConfBuilder = (SubjectConfirmationBuilder) builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
    nameIDBuilder = (NameIDBuilder) builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);

    try {
      idGenerator = new SecureRandomIdentifierGenerator();
    } catch (NoSuchAlgorithmException e) {
      throw new SamlException("Crypto algorithm missing for randomization.", e);
    }

    marshallerFactory = Configuration.getMarshallerFactory();
    stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
    kekParamsList = new ArrayList<KeyEncryptionParameters>();

    // Setup encryption if certificate was given
    if (null != recipientCert) {
      KeyEncryptionParameters kekParamsRSA = new KeyEncryptionParameters();
      kekParamsRSA.setAlgorithm(KEY_ENCRYPTION_METHOD);
      BasicCredential credential = new BasicCredential();
      credential.setPublicKey(recipientCert.getPublicKey());
      kekParamsRSA.setEncryptionCredential(credential);
      kekParamsList.add(kekParamsRSA);
    }
    // TODO: How to configure the issuer of the Saml assertion?
    issuerName = "issuer";
    // TODO: Where will the meta-data live on the C2? How do we pass down server
    // and protocol?
    idpMetaDataURL = SamlConstants.METADATA_IDP_ENDPOINT;
  }

  /**
   * Generate a Saml assertion for the given audienct, recipient and
   * destination. The attributes and remote user will be present in the
   * assertion.
   *
   * @param samlAttributes
   *          to include in the Saml assertion
   * @param remoteUser
   *          is the username as passed in assertion, cannot be null
   */
  public String generateSaml(String remoteUser, List<SamlAttribute> samlAttributes, String responseId, String audienceUri, String recipient,
      String destination, boolean signResponse, boolean signAssertion, boolean issuerInAssertion) throws SamlException {

    DateTime now = new DateTime();

    Assertion assertion = assertionBuilder.buildObject();
    assertion.setVersion(SAMLVersion.VERSION_20);

    if (null == remoteUser) {
      throw new SamlException("Remote user cannot be null when generating assertion.");
    }

    // Generate a new random id
    assertion.setID(idGenerator.generateIdentifier());
    assertion.setIssueInstant(now);

    // Set issuer
    Issuer issuer = issuerBuilder.buildObject();
    issuer.setValue(issuerName);

    // Issuer can be forced into assertion, or is automatically included if not
    // signing response
    if (!signResponse || issuerInAssertion) {
      assertion.setIssuer(issuer);
    }

    AuthnStatement authnStmt = authnStatementBuilder.buildObject();
    authnStmt.setAuthnInstant(now);
    authnStmt.setSessionIndex(idGenerator.generateIdentifier());
    AuthnContext authnContext = (AuthnContext) buildXMLObject(AuthnContext.DEFAULT_ELEMENT_NAME);
    AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
    classRef.setAuthnContextClassRef(AuthnContext.PPT_AUTHN_CTX);
    authnContext.setAuthnContextClassRef(classRef);
    authnStmt.setAuthnContext(authnContext);
    assertion.getAuthnStatements().add(authnStmt);
    NameID nameID = nameIDBuilder.buildObject();
    nameID.setNameQualifier(idpMetaDataURL);
    nameID.setValue(remoteUser);
    nameID.setFormat(SAML_NAMEID_FORMAT);
    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    subject.setNameID(nameID);
    assertion.setSubject(subject);

    AttributeStatement attributeStatement = (AttributeStatement) buildXMLObject(AttributeStatement.DEFAULT_ELEMENT_NAME);
    for (SamlAttribute samlAttribute : samlAttributes) {
      Attribute attribute = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
      attribute.setName(samlAttribute.getName());
      attribute.setNameFormat(Attribute.BASIC);
      for (String value : samlAttribute.getValues()) {
        XSString xss = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        xss.setValue(value);
        attribute.getAttributeValues().add(xss);
      }
      attributeStatement.getAttributes().add(attribute);
    }
    assertion.getAttributeStatements().add(attributeStatement);

    Audience audience = (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
    audience.setAudienceURI(audienceUri);

    AudienceRestriction ar = (AudienceRestriction) buildXMLObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);
    ar.getAudiences().add(audience);

    Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
    conditions.getAudienceRestrictions().add(ar);
    conditions.setNotBefore(now.minusSeconds(SAML_DRIFT));
    conditions.setNotOnOrAfter(now.plusSeconds(SAML_VALIDITY));

    assertion.setConditions(conditions);
    SubjectConfirmation subjConf = subjConfBuilder.buildObject();
    subjConf.setMethod(SUBJCONF_METHOD);
    QName qname = new QName(SAMLConstants.SAML20_NS, SubjectConfirmationData.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
    SubjectConfirmationData subjectCD = (SubjectConfirmationData) buildXMLObject(qname);
    subjectCD.setRecipient(recipient);
    subjectCD.setNotOnOrAfter(now.plusSeconds(SAML_VALIDITY));
    // see if this saml is in response to an authnrequest
    if (responseId != null) {
      subjectCD.setInResponseTo(responseId);
    }
    subjConf.setSubjectConfirmationData(subjectCD);
    assertion.getSubject().getSubjectConfirmations().add(subjConf);
    Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
    Status status = (Status) buildXMLObject(Status.DEFAULT_ELEMENT_NAME);
    StatusCode statusCode = (StatusCode) buildXMLObject(StatusCode.DEFAULT_ELEMENT_NAME);
    statusCode.setValue(StatusCode.SUCCESS_URI);
    status.setStatusCode(statusCode);
    response.setStatus(status);
    response.setID(idGenerator.generateIdentifier());
    response.setIssueInstant(now);
    if (responseId != null) {
      response.setInResponseTo(responseId);
    }
    if (null != destination) {
      response.setDestination(destination);
    }
    Marshaller marshaller = null;
    if (signAssertion) {
      Signature signature = signatureBuilder.buildObject();
      signature.setSigningCredential(basicCredential);
      signature.setCanonicalizationAlgorithm(CANON_ALGORITHM);
      signature.setSignatureAlgorithm(SIGNATURE_METHOD);
      assertion.setSignature(signature);
      marshaller = marshallerFactory.getMarshaller(assertion);
      try {
        marshaller.marshall(assertion);
      } catch (MarshallingException e) {
        throw new SamlException("Failed to marshall assertion.", e);
      }
      try {
        Signer.signObject(signature);
      } catch (SignatureException e) {
        throw new SamlException("Failed to sign assertion.", e);
      }
      response.getAssertions().add(assertion);
    }

    if (signResponse) {
      Signature signature = signatureBuilder.buildObject();
      signature.setSigningCredential(basicCredential);
      signature.setCanonicalizationAlgorithm(CANON_ALGORITHM);
      signature.setSignatureAlgorithm(SIGNATURE_METHOD);

      // Allow issuer to be in the response, if forced
      // (e.g. don't put the issuer in the response, per the SAML spec, if the
      // override says put it in the assertion)
      if (!issuerInAssertion) {
        response.setIssuer(issuer);
      }

      response.getAssertions().add(assertion);
      response.setSignature(signature);
      marshaller = marshallerFactory.getMarshaller(response);
      try {
        marshaller.marshall(response);
      } catch (MarshallingException e) {
        throw new SamlException("Failed to marshall response.", e);
      }
      try {
        Signer.signObject(signature);
      } catch (SignatureException e) {
        throw new SamlException("Failed to sign response.", e);
      }
    }
    marshaller = marshallerFactory.getMarshaller(response);
    Element messageDOM = null;
    try {
      messageDOM = marshaller.marshall(response);
    } catch (MarshallingException e) {
      throw new SamlException("Failed to re-marshall response.", e);
    }
    StringWriter writer = new StringWriter();
    XMLHelper.writeNode(messageDOM, writer);
    return Base64.encodeBytes(writer.toString().getBytes(SamlGenerator.CHARSET_UTF8), Base64.DONT_BREAK_LINES);
  }

  /**
   * Builds the requested SAML XMLObject.
   * 
   * @param objectQName
   *          name of the XMLObject
   * @return the built XMLObject
   */
  private static XMLObject buildXMLObject(QName objectQName) {
    XMLObjectBuilder<?> builder = Configuration.getBuilderFactory().getBuilder(objectQName);
    return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }
}