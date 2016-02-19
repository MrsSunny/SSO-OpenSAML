package org.sms.saml.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.util.io.pem.PemReader;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.impl.EntitiesDescriptorImpl;
import org.opensaml.saml2.metadata.impl.EntityDescriptorImpl;
import org.opensaml.saml2.metadata.impl.IDPSSODescriptorImpl;
import org.opensaml.saml2.metadata.impl.KeyDescriptorImpl;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Sunny
 * @since 1.8.0
 */
public class SamlUtils {

  /**
   * @param samlCert
   * @return
   */
  public static X509Certificate parsePemCertificate(String cert) {
    if (null == cert) {
      return null;
    }
    if (!cert.contains(SamlConstants.BEGIN_CERT)) {
      cert = convertCertToPemFormat(cert);
    }
    StringReader reader = new StringReader(cert);
    PemReader pem = new PemReader(reader);
    Object obj = null;
    try {
      obj = pem.readPemObject();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        pem.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (obj instanceof X509Certificate) {
      return (X509Certificate) obj;
    }
    return null;
  }

  /**
   * @param value
   * @return
   */
  public static String convertCertToPemFormat(String cert) {
    return formatPEMString(SamlConstants.BEGIN_CERT_FULL, SamlConstants.END_CERT_FULL, cert);
  }

  /**
   * @param beginCertFull
   * @param endCertFull
   * @param cert
   * @return
   */
  private static String formatPEMString(final String head, final String foot, final String indata) {
    StringBuilder pem = new StringBuilder(head);
    pem.append("\n");
    String data;
    if (indata != null) {
      data = indata.replaceAll("\\s+", "");
    } else {
      data = "";
    }
    int lineLength = 64;
    int dataLen = data.length();
    int si = 0;
    int ei = lineLength;

    while (si < dataLen) {
      if (ei > dataLen) {
        ei = dataLen;
      }
      pem.append(data.substring(si, ei));
      pem.append("\n");
      si = ei;
      ei += lineLength;
    }
    pem.append(foot);
    return pem.toString();
  }

  public static String extractCert(String metaData) throws SamlException {
    EntityDescriptorImpl md;
    try {
      md = parseMetaData(metaData);
    } catch (SamlException e) {
      throw new SamlException("Cannot parse meta data.", e);
    }

    IDPSSODescriptorImpl idp = (IDPSSODescriptorImpl) md.getIDPSSODescriptor(SAMLConstants.SAML20P_NS);
    java.util.List<KeyDescriptor> keyList = idp.getKeyDescriptors();
    KeyDescriptorImpl keyDesc = (KeyDescriptorImpl) keyList.get(0);
    KeyInfo keyInfo = keyDesc.getKeyInfo();
    java.util.List<X509Data> x509List = keyInfo.getX509Datas();
    X509Data x509Data = x509List.get(0);
    List<org.opensaml.xml.signature.X509Certificate> x509CertList = x509Data.getX509Certificates();
    org.opensaml.xml.signature.X509Certificate x509Cert = x509CertList.get(0);
    return x509Cert.getValue();
  }

  public static EntityDescriptorImpl parseMetaData(String metadataXML) throws SamlException {
    XMLObject message = unmarshallMessage(metadataXML);
    if (message instanceof EntitiesDescriptor) {
      EntitiesDescriptorImpl entitiesDesc = (EntitiesDescriptorImpl) message;
      List<EntityDescriptor> descriptors = entitiesDesc.getEntityDescriptors();
      if (descriptors != null && descriptors.size() > 0) {
        return (EntityDescriptorImpl) descriptors.get(0);
      }
      throw new SamlException("Unable to parse metadata, no EntityDescriptor found");
    } else if (message instanceof EntityDescriptor) {
      return (EntityDescriptorImpl) message;
    } else {
      throw new SamlException("Unable to parse metadata, no EntityDescriptor found");
    }
  }

  public static XMLObject unmarshallMessage(String messageXML) throws SamlException {
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(messageXML.getBytes(SamlGenerator.CHARSET_UTF8));
      ParserPool parserPool = new BasicParserPool();
      Document messageDoc = parserPool.parse(bais);
      Element messageElem = messageDoc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(messageElem);
      if (unmarshaller == null) {
        throw new SamlException("Unable to unmarshall message, no unmarshaller registered for element " + XMLHelper.getNodeQName(messageElem));
      }
      return unmarshaller.unmarshall(messageElem);
    } catch (XMLParserException e) {
      throw new SamlException("Unable to parse message into a DOM", e);
    } catch (UnmarshallingException e) {
      throw new SamlException("Unable to unmarshall message from its DOM", e);
    }
  }
}
