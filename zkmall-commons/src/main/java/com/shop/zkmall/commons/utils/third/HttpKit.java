/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils.third;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public class HttpKit {

    private static String CHARSET = "UTF-8";

    private static final SSLSocketFactory SSL_SOCKET_FACTORY = initSSLSocketFactory();

    private static final TrustAnyHostnameVerifier TRUST_ANY_HOSTNAME_VERIFIER = new TrustAnyHostnameVerifier();

//    public static final OkHttp3Delegate delegate = new OkHttp3Delegate();

    private HttpKit() {
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] e = new TrustManager[]{new TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, e, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static void setCharSet(String charSet) {
        if (StringUtils.isBlank(charSet)) {
            throw new IllegalArgumentException("charSet can not be blank.");
        } else {
            CHARSET = charSet;
        }
    }

    private static HttpURLConnection getHttpConnection(String httpUrl, String method, Map<String, String> headers) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(SSL_SOCKET_FACTORY);
            ((HttpsURLConnection) conn).setHostnameVerifier(TRUST_ANY_HOSTNAME_VERIFIER);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(19000);
        conn.setReadTimeout(19000);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("AuthUser-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        String e;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "GET", headers);
            conn.connect();
            e = readResponseString(conn);
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return e;
    }

    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;

        String var6;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            conn.connect();
            OutputStream e = conn.getOutputStream();
            e.write(data.getBytes(CHARSET));
            e.flush();
            e.close();
            var6 = readResponseString(conn);
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return var6;
    }

    public static String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, null);
    }

    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    public static String post(String url, String data) {
        return post(url, null, data, null);
    }

    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();

        try (InputStream inputStream = conn.getInputStream()) {
            BufferedReader e = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
            String line;

            while ((line = e.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (Exception var14) {
            throw new RuntimeException(var14);
        }
    }

    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas != null && !queryParas.isEmpty()) {
            StringBuilder sb = new StringBuilder(url);
            boolean isFirst;
            if (!url.contains("?")) {
                isFirst = true;
                sb.append("?");
            } else {
                isFirst = false;
            }

            String key;
            String value;
            for (Iterator<Map.Entry<String, String>> iterator = queryParas.entrySet().iterator(); iterator.hasNext(); sb.append(key).append("=").append(value)) {
                Map.Entry<String, String> entry = iterator.next();
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append("&");
                }

                key = entry.getKey();
                value = entry.getValue();
                if (StringUtils.isNotBlank(value)) {
                    try {
                        value = URLEncoder.encode(value, CHARSET);
                    } catch (UnsupportedEncodingException var9) {
                        throw new RuntimeException(var9);
                    }
                }
            }

            return sb.toString();
        } else {
            return url;
        }
    }

    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;

        try {
            StringBuilder e = new StringBuilder();
            br = request.getReader();
            String line;

            while ((line = br.readLine()) != null) {
                e.append(line).append("\n");
            }

            line = e.toString();
            return line;
        } catch (IOException var12) {
            throw new RuntimeException(var12);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignored) {
                }
            }

        }
    }

    public static String readPureBodyData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder e = new StringBuilder();
            br = request.getReader();
            String line;
            while ((line = br.readLine()) != null) {
                e.append(line);
            }
            line = e.toString();
            return line;
        } catch (IOException var12) {
            throw new RuntimeException(var12);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static String readIncommingRequestData(HttpServletRequest request) {
        return readData(request);
    }


    private static class TrustAnyTrustManager implements X509TrustManager {
        private TrustAnyTrustManager() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        private TrustAnyHostnameVerifier() {
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}
