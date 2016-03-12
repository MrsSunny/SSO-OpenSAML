package org.sms.project.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Sunny
 */
public enum SysConfig {

  INSTANCE;

  public static final String SYS_CONFIG = "config/sys/sysConfig.xml";

  private static final Map<String, List<String>> IGNOREURLS;
  
  public static final String IGNORE_KEY = "ignoreUrl";

  static {
    IGNOREURLS = new HashMap<String, List<String>>();
  }

  public void loadSysConfig() {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setValidating(false);
    DocumentBuilder db;
    try {
      db = documentBuilderFactory.newDocumentBuilder();

      /**
       * 获取文件路径
       */
      final String file = (SysConfig.class.getResource("/") + SYS_CONFIG).substring(5);
      Document doc = db.parse(new FileInputStream(new File(file)));
      XPathFactory factory = XPathFactory.newInstance();
      XPath xpath = factory.newXPath();
      String expression;
      NodeList nodeList;

      /**
       * 定义值节点
       */
      expression = "/root/ignoreUrls/*";
      nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
      List<String> list = new ArrayList<String>();

      /**
       * 获取所有需要ignore的URL值
       */
      for (int i = 0; i < nodeList.getLength(); i++) {
        list.add(nodeList.item(i).getTextContent());
      }
      
      IGNOREURLS.put(IGNORE_KEY, list);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
  }

  public List<String> getIgnoreUrls() {
    return IGNOREURLS.get(IGNORE_KEY);
  }
}