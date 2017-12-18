package org.sms;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestHtml {

    public static String getHtmlTemplate() {
        final String file = "/Users/sunny/work/github/SSO-OpenSAML/client/src/main/resources/config/template/blog.html";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line);
            }
            String fromFile = sb.toString();
            return fromFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String getMdHtml() {
        final String file = "/Users/sunny/work/github/SSO-OpenSAML/client/src/main/resources/config/template/md.html";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line+"\r\n");
            }
            String fromFile = sb.toString();
            return fromFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String a = getHtmlTemplate();
        String mdHtmlStr = getMdHtml();
        
        Document doc = Jsoup.parse(a);
        Document mdDoc = Jsoup.parse(mdHtmlStr);
//        System.out.println(mdDoc.toString());
        Elements l = mdDoc.getElementsByTag("body");
        String m = l.html().toString();
        Element singerListDiv = doc.getElementsByAttributeValue("class", "noteAd").first();
        singerListDiv.after(m);
        System.out.println(doc.toString());
    }
}
