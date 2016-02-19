package com.test.saml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Security;

import javax.xml.parsers.ParserConfigurationException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Sunny
 * @since 1.8.0
 */
public class TestRequest {

  public static String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><samlp:AuthnRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" AssertionConsumerServiceURL=\"http://soaer.com:8080\" ForceAuthn=\"false\" ID=\"A71AB3E13\" IsPassive=\"false\" IssueInstant=\"2016-01-08T11:14:32.466Z\" ProtocolBinding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\" Version=\"2.0\"><samlp:Issuer xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:assertion\">http://localhost:9080/ServiceProvider/</samlp:Issuer><saml2p:NameIDPolicy xmlns:saml2p=\"urn:oasis:names:tc:SAML:2.0:protocol\" AllowCreate=\"true\" SPNameQualifier=\"http://localhost:9080/ServiceProvider/\" /><saml2p:RequestedAuthnContext xmlns:saml2p=\"urn:oasis:names:tc:SAML:2.0:protocol\" Comparison=\"exact\"><saml:AuthnContextClassRef xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef></saml2p:RequestedAuthnContext></samlp:AuthnRequest>";

  static {
    try {
      DefaultBootstrap.bootstrap();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    Security.addProvider(new BouncyCastleProvider());
  }

  public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    String elementFile = "local.xml";
    AuthnRequest request = (AuthnRequest) unmarshallElement(elementFile);
    System.out.println(request);
  }

  /**
   * @param elementFile
   * @return
   */
  protected static XMLObject unmarshallElement(String elementFile) {
    try {
      BasicParserPool parser;
      parser = new BasicParserPool();
      parser.setNamespaceAware(true);
      Document doc = (Document) parser.parse(new ByteArrayInputStream(test.getBytes()));
      System.out.println(doc.toString());
      Element samlElement = (Element) doc.getDocumentElement();
      Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
      if (null == unmarshaller)
        throw new RuntimeException("unmarshaller的值为空");
      return unmarshaller.unmarshall(samlElement);
    } catch (XMLParserException e) {
      e.printStackTrace();
    } catch (UnmarshallingException e) {
      e.printStackTrace();
    }
    return null;
  }
}