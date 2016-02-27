package cert;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.security.Criteria;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AuthResponse {

  public AuthResponse() {
    try {
      DefaultBootstrap.bootstrap();

    } catch (ConfigurationException ce) {
      ce.printStackTrace();
    }
  }

  public HashMap<String, HashSet<String>> processResponse(String responseMessage) throws ParserConfigurationException, SAXException, IOException,
      UnmarshallingException, DecryptionException, KeyStoreException, NoSuchAlgorithmException, CertificateException, SecurityException, ValidationException,
      MetadataProviderException {
    Response fetchedResponse = fetchResponse(responseMessage);
    EncryptedAssertion encryptedAssertion = getEncryptedAssertion(fetchedResponse);
    X509Credential privateCredential = getMyKey();
    Assertion assertion = decrypt(encryptedAssertion, privateCredential, "C:/Users/anveshas/FederationMetadata.xml");
    Signature idpSignature = assertion.getSignature();
    HashMap<String, HashSet<String>> claimsContainer = getFieldsFromAssertion(assertion);

    if (idpSignature == null)
      System.err.println("Cannot extract signature from the Identity Provider");
    else
      verifyIDP(idpSignature, "C:/Users/anveshas/FederationMetadata.xml");

    return claimsContainer;
  }

  public void verifyIDP(Signature idpSignature, String federationMetadata) throws ValidationException, MetadataProviderException, ParserConfigurationException,
      SAXException, IOException, SecurityException {
    SAMLSignatureProfileValidator profileValidator = new SAMLSignatureProfileValidator();
    profileValidator.validate(idpSignature);
    SignatureValidator sigValidator = new SignatureValidator(AuthRequest.getCredential(federationMetadata));
    sigValidator.validate(idpSignature);
  }

  public X509Credential getMyKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, SecurityException {

    KeyStore keystore;
    keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    FileInputStream inputStream = new FileInputStream("C:/Users/anveshas/keystore.jks");
    keystore.load(inputStream, "anvesha".toCharArray());
    inputStream.close();
    Map<String, String> passwordMap = new HashMap<String, String>();
    passwordMap.put("openSAMLKey", "anvesha");
    KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(keystore, passwordMap);
    Criteria criteria = new EntityIDCriteria("openSAMLKey");
    CriteriaSet criteriaSet = new CriteriaSet(criteria);
    X509Credential credential = (X509Credential) resolver.resolveSingle(criteriaSet);
    return credential;

  }

  public Response fetchResponse(String responseMessage) throws ParserConfigurationException, SAXException, IOException, UnmarshallingException {

    Base64 base64 = new Base64();
    byte[] decodedB = base64.decode(responseMessage);
    ByteArrayInputStream is = new ByteArrayInputStream(decodedB);
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(true);
    DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = docBuilder.parse(is);
    Element element = document.getDocumentElement();
    Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(element);
    XMLObject xmlobj = unmarshaller.unmarshall(element);
    Response respObject = (Response) xmlobj;
    System.out.println(respObject);
    return respObject;
  }

  public EncryptedAssertion getEncryptedAssertion(Response respObject) {
    List<EncryptedAssertion> encryptedAssertion = respObject.getEncryptedAssertions();
    EncryptedAssertion firstAssertion = encryptedAssertion.get(0);
    System.out.println(encryptedAssertion.get(0));
    EncryptedKey key = firstAssertion.getEncryptedData().getKeyInfo().getEncryptedKeys().get(0);
    System.out.println(key.toString());
    return firstAssertion;
  }

  /*
   * public String getSignatureFromAssertion(Assertion assertion) {
   * 
   * Element domElement = assertion.getDOM(); NodeList nodeList =
   * domElement.getChildNodes();
   * 
   * for (int i=0; i<nodeList.getLength(); i++) { if
   * (nodeList.item(i).getNodeName().toString() == "Signature") { return
   * nodeList.item(i).getTextContent(); }
   * 
   * else continue; //System.out.println(nodeList.item(i).getTextContent()); }
   * 
   * return null; }
   */

  public HashMap<String, HashSet<String>> getFieldsFromAssertion(Assertion assertion) {

    HashMap<String, HashSet<String>> claimsContainer = new HashMap<String, HashSet<String>>();
    Element domElement = assertion.getDOM();
    NodeList nodeList = domElement.getChildNodes();
    // System.out.println(nodeList.getLength());

    for (int i = 0; i < nodeList.getLength(); i++) {
      if (nodeList.item(i).getNodeName().toString() == "AttributeStatement") {
        NodeList claimNodes = nodeList.item(i).getChildNodes();
        for (int j = 0; j < claimNodes.getLength(); j++) {

          String key = claimNodes.item(j).getAttributes().item(0).getTextContent();
          NodeList childClaimNodes = claimNodes.item(j).getChildNodes();
          HashSet<String> values = new HashSet<String>();

          for (int k = 0; k < childClaimNodes.getLength(); k++) {
            values.add(childClaimNodes.item(k).getTextContent());
          }
          claimsContainer.put(key, values);
        }
      }

      else
        continue;
      // System.out.println(nodeList.item(i).getTextContent());
    }
    return claimsContainer;
  }

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
}