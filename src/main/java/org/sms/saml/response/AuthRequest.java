package org.sms.saml.response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Condition;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.OneTimeUse;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.AssertionMarshaller;
import org.opensaml.saml2.core.impl.EncryptedAssertionMarshaller;
import org.opensaml.saml2.encryption.Encrypter;
import org.opensaml.saml2.encryption.Encrypter.KeyPlacement;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.provider.DOMMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.security.MetadataCredentialResolverFactory;
import org.opensaml.security.MetadataCriteria;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This is a demo class which creates a valid SAML 2.0 Assertion.
 */
public class AuthRequest {

  public AuthRequest() {
    try {
      DefaultBootstrap.bootstrap();
      System.out.println("Initialized the libraries");

    } catch (Exception ce) {
      ce.printStackTrace();
    }
  }

  public String samlWriter() throws MarshallingException, ParserConfigurationException, SAXException, IOException, MetadataProviderException,
      SecurityException, NoSuchAlgorithmException, KeyException, EncryptionException {
    try {
      SAMLInputContainer input = new SAMLInputContainer();
      input.strIssuer = "https://localhost:8443/openSAML/index.jsp";
      input.strNameID = "Anvesha Sinha";
      input.strNameQualifier = "openSAML";
      input.sessionId = "abcdedf1234567";

      Map<String, String> customAttributes = new HashMap<String, String>();
      customAttributes.put("FirstName", "Anvesha");
      customAttributes.put("LastName", "Sinha");
      input.attributes = customAttributes;
      Assertion assertion = AuthRequest.buildDefaultAssertion(input);
      AssertionMarshaller marshaller = new AssertionMarshaller();
      Element plaintextElement = marshaller.marshall(assertion);
      String originalAssertionString = XMLHelper.nodeToString(plaintextElement);
      System.out.println("Assertion String: " + originalAssertionString);
      // Do the encryption
      X509Credential credential = getCredential("C:/Users/anveshas/FederationMetadata.xml");
      System.out.println(credential.getEntityCertificate().getPublicKey());
      EncryptedAssertion encryptedAssertion = encrypt(assertion, credential);
      EncryptedAssertionMarshaller marshallerEncryptedAssertion = new EncryptedAssertionMarshaller();
      Element plaintextEncryptedAssertion = marshallerEncryptedAssertion.marshall(encryptedAssertion);
      String encryptedAssertionString = XMLHelper.nodeToString(plaintextEncryptedAssertion);
      System.out.println("Encrypted Assertion String: " + encryptedAssertionString);
      return encryptedAssertionString;
    } finally {

    }
  }
  
  public static void main(String[] args) throws NoSuchAlgorithmException, KeyException, MarshallingException, ParserConfigurationException, SAXException, IOException, MetadataProviderException, SecurityException, EncryptionException {
    AuthRequest request = new AuthRequest();
    System.out.println(request.samlWriter());
  }

  private static XMLObjectBuilderFactory builderFactory;

  public static XMLObjectBuilderFactory getSAMLBuilder() throws ConfigurationException {

    if (builderFactory == null) {
      DefaultBootstrap.bootstrap();
      builderFactory = Configuration.getBuilderFactory();
    }

    return builderFactory;
  }

  /**
   * Builds a SAML Attribute of type String
   * 
   * @param name
   * @param value
   * @param builderFactory
   * @return
   * @throws ConfigurationException
   */
  public static Attribute buildStringAttribute(String name, String value, XMLObjectBuilderFactory builderFactory) throws ConfigurationException {
    SAMLObjectBuilder attrBuilder = (SAMLObjectBuilder) getSAMLBuilder().getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
    Attribute attrFirstName = (Attribute) attrBuilder.buildObject();
    attrFirstName.setName(name);

    // Set custom Attributes
    XMLObjectBuilder stringBuilder = getSAMLBuilder().getBuilder(XSString.TYPE_NAME);
    XSString attrValueFirstName = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
    attrValueFirstName.setValue(value);

    attrFirstName.getAttributeValues().add(attrValueFirstName);
    return attrFirstName;
  }

  /**
   * Helper method which includes some basic SAML fields which are part of
   * almost every SAML Assertion.
   *
   * @param input
   * @return
   */
  public static Assertion buildDefaultAssertion(SAMLInputContainer input) {
    try {
      // Create the NameIdentifier
      SAMLObjectBuilder nameIdBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(NameID.DEFAULT_ELEMENT_NAME);
      NameID nameId = (NameID) nameIdBuilder.buildObject();
      nameId.setValue(input.getStrNameID());
      nameId.setNameQualifier(input.getStrNameQualifier());
      nameId.setFormat(NameID.UNSPECIFIED);

      // Create the SubjectConfirmation

      SAMLObjectBuilder confirmationMethodBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
      SubjectConfirmationData confirmationMethod = (SubjectConfirmationData) confirmationMethodBuilder.buildObject();
      DateTime now = new DateTime();
      confirmationMethod.setNotBefore(now);
      confirmationMethod.setNotOnOrAfter(now.plusMinutes(2));

      SAMLObjectBuilder subjectConfirmationBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
      SubjectConfirmation subjectConfirmation = (SubjectConfirmation) subjectConfirmationBuilder.buildObject();
      subjectConfirmation.setSubjectConfirmationData(confirmationMethod);

      // Create the Subject
      SAMLObjectBuilder subjectBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(Subject.DEFAULT_ELEMENT_NAME);
      Subject subject = (Subject) subjectBuilder.buildObject();

      subject.setNameID(nameId);
      subject.getSubjectConfirmations().add(subjectConfirmation);

      // Create Authentication Statement
      SAMLObjectBuilder authStatementBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
      AuthnStatement authnStatement = (AuthnStatement) authStatementBuilder.buildObject();
      // authnStatement.setSubject(subject);
      // authnStatement.setAuthenticationMethod(strAuthMethod);
      DateTime now2 = new DateTime();
      authnStatement.setAuthnInstant(now2);
      authnStatement.setSessionIndex(input.getSessionId());
      authnStatement.setSessionNotOnOrAfter(now2.plus(input.getMaxSessionTimeoutInMinutes()));

      SAMLObjectBuilder authContextBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME);
      AuthnContext authnContext = (AuthnContext) authContextBuilder.buildObject();

      SAMLObjectBuilder authContextClassRefBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
      AuthnContextClassRef authnContextClassRef = (AuthnContextClassRef) authContextClassRefBuilder.buildObject();
      authnContextClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:Password"); // TODO
                                                                                                       // not
                                                                                                       // sure
                                                                                                       // exactly
                                                                                                       // about
                                                                                                       // this

      authnContext.setAuthnContextClassRef(authnContextClassRef);
      authnStatement.setAuthnContext(authnContext);

      // Builder Attributes
      SAMLObjectBuilder attrStatementBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
      AttributeStatement attrStatement = (AttributeStatement) attrStatementBuilder.buildObject();

      // Create the attribute statement
      Map attributes = input.getAttributes();
      if (attributes != null) {
        Set keySet = attributes.keySet();
        for (Object key : keySet) {
          Attribute attrFirstName = buildStringAttribute((String) key, (String) attributes.get(key), getSAMLBuilder());
          attrStatement.getAttributes().add(attrFirstName);
        }
      }

      // Create the do-not-cache condition
      SAMLObjectBuilder doNotCacheConditionBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(OneTimeUse.DEFAULT_ELEMENT_NAME);
      Condition condition = (Condition) doNotCacheConditionBuilder.buildObject();

      SAMLObjectBuilder conditionsBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
      Conditions conditions = (Conditions) conditionsBuilder.buildObject();
      conditions.getConditions().add(condition);

      // Create Issuer
      SAMLObjectBuilder issuerBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
      Issuer issuer = (Issuer) issuerBuilder.buildObject();
      issuer.setValue(input.getStrIssuer());

      // Create the assertion
      SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder) AuthRequest.getSAMLBuilder().getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
      Assertion assertion = (Assertion) assertionBuilder.buildObject();
      assertion.setIssuer(issuer);
      assertion.setIssueInstant(now);
      assertion.setVersion(SAMLVersion.VERSION_20);

      assertion.getAuthnStatements().add(authnStatement);
      assertion.getAttributeStatements().add(attrStatement);
      assertion.setConditions(conditions);

      return assertion;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static X509Credential getCredential(String federationMetadata) throws ParserConfigurationException, SAXException, IOException,
      MetadataProviderException, SecurityException {

    // The Meta data resolver helps to extract public credentials from meta data

    // First we create a meta data provider.
    InputStream metadataInputStream = new FileInputStream(federationMetadata);
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);
    DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

    Document metadataDocument = docBuilder.parse(metadataInputStream);
    Element metadataRoot = metadataDocument.getDocumentElement();
    metadataInputStream.close();

    DOMMetadataProvider idpMetadataProvider = new DOMMetadataProvider(metadataRoot);
    idpMetadataProvider.setRequireValidMetadata(true);
    idpMetadataProvider.setParserPool(new BasicParserPool());
    idpMetadataProvider.initialize();

    // Resolve the credential
    MetadataCredentialResolverFactory credentialResolverFactory = MetadataCredentialResolverFactory.getFactory();

    MetadataCredentialResolver credentialResolver = credentialResolverFactory.getInstance(idpMetadataProvider);

    CriteriaSet criteriaSet = new CriteriaSet();
    criteriaSet.add(new MetadataCriteria(IDPSSODescriptor.DEFAULT_ELEMENT_NAME, SAMLConstants.SAML20P_NS));
    criteriaSet.add(new EntityIDCriteria("http://colo-pm2.adx.isi.edu/adfs/services/trust"));
    criteriaSet.add(new UsageCriteria(UsageType.SIGNING));

    X509Credential credential = (X509Credential) credentialResolver.resolveSingle(criteriaSet);
    return credential;
  }

  public EncryptedAssertion encrypt(Assertion assertion, X509Credential receiverCredential) throws EncryptionException, NoSuchAlgorithmException, KeyException {

    Credential symmetricCredential = SecurityHelper.getSimpleCredential(SecurityHelper.generateSymmetricKey(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128));

    // The EncryptionParameters contain a reference to the shared key and the
    // algorithm to use
    EncryptionParameters encParams = new EncryptionParameters();
    encParams.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
    encParams.setEncryptionCredential(symmetricCredential);

    // The KeyEncryptionParameters contain the receiver's public key
    KeyEncryptionParameters kek = new KeyEncryptionParameters();
    kek.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
    kek.setEncryptionCredential(receiverCredential);

    Encrypter encrypter = new Encrypter(encParams, kek);
    encrypter.setKeyPlacement(KeyPlacement.INLINE);

    EncryptedAssertion encrypted = encrypter.encrypt(assertion);

    return encrypted;
    // response.getEncryptedAssertions().add(encrypted);
    //
    // response.getAssertions().clear();
    // System.out.println(response);
  }

  public static class SAMLInputContainer {

    private String strIssuer;
    private String strNameID;
    private String strNameQualifier;
    private String sessionId;
    private int maxSessionTimeoutInMinutes = 15; // default is 15 minutes

    private Map attributes;

    /**
     * Returns the strIssuer.
     *
     * @return the strIssuer
     */
    public String getStrIssuer() {
      return strIssuer;
    }

    /**
     * Sets the strIssuer.
     *
     * @param strIssuer
     *          the strIssuer to set
     */
    public void setStrIssuer(String strIssuer) {
      this.strIssuer = strIssuer;
    }

    /**
     * Returns the strNameID.
     *
     * @return the strNameID
     */
    public String getStrNameID() {
      return strNameID;
    }

    /**
     * Sets the strNameID.
     *
     * @param strNameID
     *          the strNameID to set
     */
    public void setStrNameID(String strNameID) {
      this.strNameID = strNameID;
    }

    /**
     * Returns the strNameQualifier.
     *
     * @return the strNameQualifier
     */
    public String getStrNameQualifier() {
      return strNameQualifier;
    }

    /**
     * Sets the strNameQualifier.
     *
     * @param strNameQualifier
     *          the strNameQualifier to set
     */
    public void setStrNameQualifier(String strNameQualifier) {
      this.strNameQualifier = strNameQualifier;
    }

    /**
     * Sets the attributes.
     *
     * @param attributes
     *          the attributes to set
     */
    public void setAttributes(Map attributes) {
      this.attributes = attributes;
    }

    /**
     * Returns the attributes.
     *
     * @return the attributes
     */
    public Map getAttributes() {
      return attributes;
    }

    /**
     * Sets the sessionId.
     * 
     * @param sessionId
     *          the sessionId to set
     */
    public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
    }

    /**
     * Returns the sessionId.
     * 
     * @return the sessionId
     */
    public String getSessionId() {
      return sessionId;
    }

    /**
     * Sets the maxSessionTimeoutInMinutes.
     * 
     * @param maxSessionTimeoutInMinutes
     *          the maxSessionTimeoutInMinutes to set
     */
    public void setMaxSessionTimeoutInMinutes(int maxSessionTimeoutInMinutes) {
      this.maxSessionTimeoutInMinutes = maxSessionTimeoutInMinutes;
    }

    /**
     * Returns the maxSessionTimeoutInMinutes.
     * 
     * @return the maxSessionTimeoutInMinutes
     */
    public int getMaxSessionTimeoutInMinutes() {
      return maxSessionTimeoutInMinutes;
    }

  }

}