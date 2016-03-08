package org.sms.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.sms.SysConstants;

public final class HttpUtil {

  public static String doPost(String url, String postData) {
    BufferedReader read = null;
    String responseResult = null;
    try {
      URL realurl = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) realurl.openConnection();
      connection.setConnectTimeout(20 * 1000);
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      connection.connect();
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.write(postData.getBytes());
      out.flush();
      out.close();
      read = new BufferedReader(new InputStreamReader(connection.getInputStream(), SysConstants.CHARSET));
      String line;
      StringBuffer result = new StringBuffer();
      while ((line = read.readLine()) != null) {
        result.append(line);
      }
      responseResult = result.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (read != null) {
        try {
          read.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return responseResult;
  }

  public static String readInputStream(InputStream inStream) throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = inStream.read(buffer)) != -1) {
      outStream.write(buffer, 0, len);
    }
    byte[] data = outStream.toByteArray();
    outStream.close();
    inStream.close();
    return new String(data);
  }
}