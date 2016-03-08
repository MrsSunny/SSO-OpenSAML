package org.sms.saml.service;

import java.io.IOException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;

import javax.xml.parsers.ParserConfigurationException;

import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.SignableXMLObject;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.validation.ValidationException;
import org.xml.sax.SAXException;

public interface SamlService {

  /**
   * 转换XMLObject-->String
   * @param xmlObject
   * @return
   */
  String buildXMLObjectToString(XMLObject xmlObject);
  
  /**
   * 转换xmlObjectString-->XMLObject
   * @param xmlObjectString
   * @return
   */
  XMLObject buildStringToXMLObject(String xmlObjectString);
  
  /**
   * 获取AuthnRequest对象
   * @return
   */
  AuthnRequest buildAuthnRequest(String setAssertionConsumerServiceURL);
  
  /**
   * 获取Response对象
   * @param requestId
   * @param audienceURI
   * @return
   */
  Response buildResponse(String requestId, String audienceURI);
  
  /**
   * 为断言添加属性
   * @param name
   * @param value
   * @return
   */
  Attribute buildStringAttribute(String name, String value);

  /**
   * 获取Artifact对象
   */
  Artifact buildArtifact();
  
  ArtifactResolve buildArtifactResolve();
  
  ArtifactResponse buildArtifactResponse();
  
  /**
   * 解析SSO根据xml的localPath
   * @param xmlFilePath
   * @param descriptorType
   * @return
   */
  SSODescriptor buildSSODescriptor(String xmlFilePath, Class<?> descriptorType);

  /**
   * 获取RSA的公钥
   * @return
   */
  PublicKey getRSAPublicKey();

  /**
   * 获取RSA的私钥
   * @return
   */
  RSAPrivateKey getRSAPrivateKey();
  
  void signXMLObject(SignableXMLObject signableXMLObject);

  /**
   * 获取X509证书
   * @param xmlFilePath
   * @return
   */
  X509Certificate getX509Certificate(String xmlFilePath);

  XMLObject unmarshallElementWithXMLFile(String elementFile);

  /**
   * 加密断言
   * @param assertion
   * @param receiverCredential
   * @return
   * @throws EncryptionException
   * @throws NoSuchAlgorithmException
   * @throws KeyException
   */
  EncryptedAssertion encrypt(Assertion assertion, X509Credential receiverCredential);

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
  Assertion decrypt(EncryptedAssertion enc, Credential credential, String federationMetadata);

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
  Signature signature();

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
  boolean validate(String base64Response);
  
  Status getStatusCode(boolean success);
}