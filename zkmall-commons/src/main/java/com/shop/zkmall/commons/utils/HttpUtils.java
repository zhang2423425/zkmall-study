/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;

import com.google.gson.Gson;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.express.HttpResult;
import com.shop.zkmall.commons.exception.CoBusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zkmall zkmallvip@163.com
 */
public class HttpUtils {

    private final static String CHARSET_DEFAULT = "UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * post请求  编码格式默认UTF-8
     *
     * @param url 请求url
     */
    public static HttpResult doPost(String url, Object obj) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse resp = null;

        HttpResult result = new HttpResult();
        try {
            Map<String, String> params = objectToMap(obj);
            HttpPost httpPost = new HttpPost(url);
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(list, CHARSET_DEFAULT));
            }

            resp = httpClient.execute(httpPost);
            String body = EntityUtils.toString(resp.getEntity(), CHARSET_DEFAULT);
            int statusCode = resp.getStatusLine().getStatusCode();

            result.setStatus(statusCode);
            result.setBody(body);
        } catch (Exception e) {
            LOGGER.error("调用{}出现错误:", url, e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR, e.getMessage());
        } finally {
            if (null != resp) {
                try {
                    resp.close();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * post请求 参数为json形式  编码格式默认UTF-8
     *
     * @param url 请求url
     */
    public static HttpResult doPostJson(String url, Object obj) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        CloseableHttpResponse resp = null;

        HttpResult result = new HttpResult();
        try {

            HttpPost httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(obj.toString());
            entity.setContentType("text/json");
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(entity);

            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            resp = httpClient.execute(httpPost);
            String body = EntityUtils.toString(resp.getEntity(), CHARSET_DEFAULT);
            int statusCode = resp.getStatusLine().getStatusCode();

            result.setStatus(statusCode);
            result.setBody(body);
        } catch (Exception e) {
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR, e.getMessage());
        } finally {
            if (null != resp) {
                try {
                    resp.close();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     */
    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue;
            if (field.getType() == String.class || field.getType() == Integer.class || field.getType() == int.class) {
                fieldValue = field.get(obj) == null ? "" : field.get(obj).toString();
            } else {
                fieldValue = new Gson().toJson(field.get(obj));
            }
            map.put(fieldName, fieldValue);
        }
        return map;
    }

    public static void write(InputStream inputStream, HttpServletResponse response) throws IOException {
        try (InputStream is = inputStream) {
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int len;
            while ((len = bis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            bos.flush();
        }
    }


    // 获取客户端ip
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.isEmpty() || ip.contains(":")) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = null;
            }
        }
        if (ip != null && !ip.isEmpty()) {
            String[] ipArr = ip.split(",");
            for (String ipCur : ipArr) {
                if (!"127.0.0.1".equals(ipCur.trim())) {
                    ip = ipCur.trim();
                    break;
                }
            }
        }
        return ip;
    }
}
