package org.sms.project.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.sms.SysConstants;

/**
 * http请求工具类
 * 
 * @author Sunny
 */
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

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "" : ip;
    }
}