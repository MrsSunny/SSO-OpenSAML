package org.sms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * 压缩解压工具类
 * @author Sunny
 */
public class GZipUtil {

  /**
   * 使用gzip进行压缩
   */
  public static String gzip(String primStr) {

    if (primStr == null || primStr.length() == 0) {
      return primStr;
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip = null;
    try {
      gzip = new GZIPOutputStream(out);
      gzip.write(primStr.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (gzip != null) {
        try {
          gzip.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return Base64.encodeBase64String(out.toByteArray());
  }

  /**
   * Description:使用gzip进行解压缩
   * 
   * @param compressedStr
   * @return
   */
  public static String gunzip(String compressedStr) {
    if (compressedStr == null) {
      return null;
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = null;
    GZIPInputStream ginzip = null;
    byte[] compressed = null;
    String decompressed = null;
    try {
      compressed = Base64.decodeBase64(compressedStr);
      in = new ByteArrayInputStream(compressed);
      ginzip = new GZIPInputStream(in);

      byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = ginzip.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (ginzip != null) {
          ginzip.close();
        }
        if (in != null) {
          in.close();
        }
        if (out != null) {
          out.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return decompressed;
  }
}