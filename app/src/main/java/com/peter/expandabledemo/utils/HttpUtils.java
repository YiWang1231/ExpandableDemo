package com.peter.expandabledemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtils {
    public static String doGet(String urlStr) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                baos = new ByteArrayOutputStream();
                is = conn.getInputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len=is.read(buffer))!=-1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                return baos.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos!=null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}