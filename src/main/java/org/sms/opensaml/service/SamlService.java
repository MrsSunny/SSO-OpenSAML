package org.sms.opensaml.service;

import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;

import org.opensaml.saml2.core.Artifact;
import org.opensaml.saml2.core.ArtifactResolve;
import org.opensaml.saml2.core.ArtifactResponse;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.signature.SignableXMLObject;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.X509Certificate;
import org.sms.project.user.entity.User;

/**
 * @author Sunny
 */
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
  AuthnRequest buildAuthnRequest(String requestId, String setAssertionConsumerServiceURL);
  
  /**
   * 获取Response对象
   * @param requestId
   * @param audienceURI
   * @return
   */
  Response buildResponse(String requestId);
  
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
  
  /**
   * artifact Bindings
   * @return
   */
  ArtifactResolve buildArtifactResolve();
  
  /**
   * @return
   */
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
  
  /**
   * 给可签名的XML Object做签名
   * @param signableXMLObject
   */
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
   */
  EncryptedAssertion encrypt(Assertion assertion, X509Credential receiverCredential);

  /**
   * 解密断言
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   */
  Assertion decrypt(EncryptedAssertion enc, Credential credential, String federationMetadata);

  /**
   * 签名断言
   * @param enc
   * @param credential
   * @param federationMetadata
   * @return
   */
  Signature signature();

  /**
   * 验签断言
   * @param base64Response
   * @return
   */
  boolean validate(String base64Response);
  
  /**
   * 验签断言
   * @param signableXMLObject
   * @return
   */
  boolean validate(SignableXMLObject signableXMLObject);
  
  /**
   * 获取StatusCode
   * @param success
   * @return
   */
  Status getStatusCode(boolean success);
  
  /**
   * 添加属性
   * @param response
   * @param user
   */
  void addAttribute(Response response, User user);

  /**
   * 进行属性请求查询
   */
  AttributeQuery buildAttributeQuery();
}