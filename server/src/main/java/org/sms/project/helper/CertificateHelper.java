package org.sms.project.helper;

import java.security.KeyException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.sms.SysConstants;
import org.sms.opensaml.service.impl.SamlServiceImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 证书相关操作类
 * @author Sunny
 */
public class CertificateHelper {

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

  public static PublicKey getRSAPublicKey() {
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
  
  public static RSAPrivateKey getRSAPrivateKey() {
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

  public static SSODescriptor buildSSODescriptor(String xmlFilePath, Class<?> descriptorType) {
    EntityDescriptor entityDescriptor = (EntityDescriptor) unmarshallElementWithXMLFile(xmlFilePath);
    if (descriptorType.getClass().getName().equals(IDPSSODescriptor.class.getName())) {
      return entityDescriptor.getIDPSSODescriptor("urn:oasis:names:tc:SAML:2.0:protocol");
    }
    return entityDescriptor.getSPSSODescriptor("urn:oasis:names:tc:SAML:2.0:protocol");
  }

  public static XMLObject unmarshallElementWithXMLFile(String elementFile) {
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
}
