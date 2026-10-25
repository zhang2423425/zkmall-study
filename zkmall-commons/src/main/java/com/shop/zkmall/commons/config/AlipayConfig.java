/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties("alipay")
public class AlipayConfig {

    // 请求网关地址
    public static final String URL = "https://openapi.alipay.com/gateway.do";

    // 编码
    public static final String CHARSET = "UTF-8";

    // 返回格式
    public static final String FORMAT = "json";

    // 日志记录目录定义在 logFile 中
    public static final String log_path = "/log";

    // RSA2
    public static final String SIGNTYPE = "RSA2";

    /**
     * 应用appid
     */
    private String appId;

    /**
     * 应用私钥
     */
    private String rsaPrivateKey;

    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;

    /**
     * 接口内容解密key
     */
    private String decryptKey;

    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
     */
    private String redirectUrl;

    /**
     * 退款回调地址
     */
    private String refundNotifyUrl;

    /**
     * 支付成功后端回调地址
     */
    private String appNotifyUrl;

    /**
     * 平台活动保证金支付成功回调地址
     */
    private String bondNotifyUrl;

    /**
     * 花呗手续费支付方式 1-商户支付 2-用户支付
     */
    private Integer huabeiChargeType;

    /**
     * 花呗手续费比例列表 3期 6期 12期
     */
    private List<Double> huabeiFeerateList;


}
