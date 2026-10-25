/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.app.param.index.LoginPhoneParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j(topic = "WechatUtil")
@Component
public class WechatUtil {

    /**
     * 支付小程序appid
     */
    private static String appId;

    /**
     * 支付小程序秘钥
     */
    private static String secret;

    @Value("${weixin.appid}")
    public void setAppId(String appId) {
        WechatUtil.appId = appId;
    }

    @Value("${weixin.secret}")
    public void setSecret(String secret) {
        WechatUtil.secret = secret;
    }

    /**
     * 获取小程序codeid换取openid
     */
    public static JSONObject getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" +
                secret + "&js_code=" + code + "&grant_type=authorization_code";
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuilder stringBuffer = new StringBuilder();
        try {
            URLConnection conn = getUrlConnection(url);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
            //log.info("jsonObject:" + JSON.toJSONString(jsonObject));
            log.info("getOpenId:{}", jsonObject.toString());
            return jsonObject;

        } catch (Exception e) {
            log.error("getOpenId:", e);
            throw new RuntimeException(e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * 获取小程序token
     */
    public static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "appid=" + appId + "&secret=" + secret + "&grant_type=client_credential";
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuilder stringBuffer = new StringBuilder();
        try {
            URLConnection conn = getUrlConnection(url);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
            return jsonObject.getString("access_token");

        } catch (Exception e) {
            log.error("getAccessToken:", e);
            throw new RuntimeException(e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    @NotNull
    private static URLConnection getUrlConnection(String url) throws IOException {
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();

        // 设置通用的请求属性 设置请求格式
        //设置返回类型
        conn.setRequestProperty("contentType", "text/plain");
        //设置请求类型
        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        //设置超时时间
        conn.setConnectTimeout(1000);
        conn.setReadTimeout(1000);
        conn.setDoOutput(true);
        conn.connect();
        return conn;
    }


    /**
     * 获取微信小程序 session_key 和 openid
     *
     * @param code 调用微信登陆返回的Code
     */
    public static JSONObject getSessionKeyOropenid(String code) {
        //微信端登录code值
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", appId);
        requestUrlParam.put("secret", secret);
        requestUrlParam.put("js_code", code);
        requestUrlParam.put("grant_type", "authorization_code");

        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        return JSON.parseObject(sendPost(requestUrl, requestUrlParam));
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        StringBuilder param = new StringBuilder();

        for (String key : paramMap.keySet()) {
            param.append(key).append("=").append(paramMap.get(key)).append("&");
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
        return result.toString();
    }

    public static BufferedImage getWxacode(String accessToken, String path) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("path", path);
        paramMap.put("width", "120");
        String Json = JSONObject.toJSONString(paramMap);
        BufferedImage qrCodeImage = null;
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("accept", "*/*");
            if (!TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                conn.getResponseCode();
            }
            if (conn.getResponseCode() == 200) {
                qrCodeImage = ImageIO.read(conn.getInputStream());
            }
        } catch (Exception e) {
            log.error("getWxacode", e);
            throw new RuntimeException(e);
        }
        return qrCodeImage;
    }

    public static Map<String, Object> getPhoneNumber(LoginPhoneParam vo) {
        Map<String, Object> map = new HashMap<>();
        String openid = vo.getWechatOpenId();
        String sessionKey = vo.getSessionKey();
        if (!EmptyUtils.isEmpty(openid)) {

            if (EmptyUtils.isEmpty(sessionKey)) {
                return null;
            }
            map.put("openid", openid);
            // 被加密的数据
            byte[] dataByte = Base64.getDecoder().decode(vo.getEncryptedData());
            // 加密秘钥
            byte[] keyByte = Base64.getDecoder().decode(sessionKey);
            // 偏移量
            byte[] ivByte = Base64.getDecoder().decode(vo.getIv());
            try {
                // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
                int base = 16;
                if (keyByte.length % base != 0) {
                    int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                    byte[] temp = new byte[groups * base];
                    Arrays.fill(temp, (byte) 0);
                    System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                    keyByte = temp;
                }
                // 初始化
                Security.addProvider(new BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
                AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);
                parameters.init(ivParameterSpec);
                cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
                byte[] resultByte = cipher.doFinal(dataByte);
                if (null != resultByte && resultByte.length > 0) {
                    String result = new String(resultByte, StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    map.put("param", jsonObject);
                    return map;
                }
            } catch (Exception e) {
                log.error("getPhoneNumber", e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
