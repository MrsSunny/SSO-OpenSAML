package org.sms.project.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.sms.SysConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Sunny
 */
public enum SysConfig {

    INSTANCE;

    private static AtomicBoolean ISINIT = new AtomicBoolean(false);

    private static final Map<String, List<String>> IGNOREURLS = new HashMap<String, List<String>>();
    
    private static final Map<String, String> cache = new HashMap<String, String>();

    public void loadSysConfig() {

        if (ISINIT.compareAndSet(false, true)) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            DocumentBuilder db;
            try {
                db = documentBuilderFactory.newDocumentBuilder();

                /**
                 * 获取文件路径
                 */
                final String file = (SysConfig.class.getResource("/") + SysConstants.SYS_CONFIG).substring(5);
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

                IGNOREURLS.put(SysConstants.IGNORE_KEY, list);
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
    }

    public void loadHtmlTemplate() {
        final String file = (SysConfig.class.getResource("/") + SysConstants.BLOG_TEMPLATE).substring(5);
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line + "\r\n");
            }
            String fromFile = sb.toString();
            cache.put(SysConstants.BLOH_HTML_KEY, fromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getCacheDate(String key) {
        return cache.get(key);
    }

    public List<String> getIgnoreUrls() {
        return IGNOREURLS.get(SysConstants.IGNORE_KEY);
    }
}