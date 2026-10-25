/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.miaoxin.sdk;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 秒信业务客户端SDK 基类
 *
 * @author zkmall zkmallvip@163.com
 */
public abstract class MiaoxinClient {

    public abstract String getServerHost();

    public abstract String getAccount();

    public abstract String getPassword();

    /**
     * 账号余额查询
     */
    public String balance() throws IOException {
        String interfaceName = "/account/getBalance";
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String result = request(interfaceName, getAccount(), getPassword(), params);
        // 返回的JSON中，RMP，即剩余
        return result;
    }

    protected String request(String interfaceName, String account, String secret, Map<String, Object> params) throws IOException {
        params.put("account", account);
        String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        params.put("ts", ts);
        String token = sha1("account=" + account + "&ts=" + ts + "&secret=" + secret);
        params.put("token", token);
        return post(getServerHost() + interfaceName, params, "UTF-8");
    }

    protected String sha1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected String post(String action, Map<String, Object> params, String encoding) throws IOException {
        if (params.isEmpty()) {
            post(action, "", 2000, 10000);
        }
        StringBuilder content = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        Map.Entry<String, Object> entry = null;
        while (it.hasNext()) {
            entry = it.next();
            Object value = entry.getValue();
            if (value instanceof String) {
                content.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode((String) value, encoding));
            } else {
                content.append("&").append(entry.getKey()).append("=").append(value);
            }
        }
        content.deleteCharAt(0);
        return post(action, content.toString(), 2000, 10000);
    }

    /**
     * 发送请求数据
     */
    protected String post(String action, String content, int timeout4Connect, int timeout4Read) throws IOException {
        URL url = new URL(action);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(timeout4Connect);
        conn.setReadTimeout(timeout4Read);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.connect();
        OutputStream outputStream = conn.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes(content);
        dataOutputStream.flush();
        dataOutputStream.close();
        outputStream.close();
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer response = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        conn.disconnect();

        return response.toString();
    }
}
