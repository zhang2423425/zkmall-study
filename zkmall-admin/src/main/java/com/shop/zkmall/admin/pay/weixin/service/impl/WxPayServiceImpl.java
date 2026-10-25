/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.pay.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.admin.pay.weixin.service.WxPayService;
import com.shop.zkmall.commons.constant.WxPayEnum;
import com.shop.zkmall.commons.utils.third.PaymentKit;
import com.shop.zkmall.commons.utils.third.WXPayUtil;
import com.shop.zkmall.commons.utils.third.WXPayV3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxPayServiceImpl implements WxPayService {

    /**
     * 支付小程序appid
     */
    @Value("${weixin.appid}")
    private String appid;

    /**
     * 支付APP端的appid
     */
    @Value("${weixin.app_appid}")
    private String app_appid;

    /**
     * 支付小程序秘钥
     */
    @Value("${weixin.secret}")
    private String secret;

    /**
     * 商户号
     */
    @Value("${weixin.mchid}")
    private String mch_id;

    /**
     * 证书路径
     */
    @Value("${weixin.certurl}")
    private String cert_url;

    /**
     * pc回调地址
     */
    @Value("${weixin.pc_notifyurl}")
    private String pc_notify_url;

    /**
     * pc退款回调地址
     */
    @Value("${weixin.pc_refund_notifyurl}")
    private String pc_refund_notify_url;

    /**
     * pc保证金退款回调地址
     */
    @Value("${weixin.pc_bond_refund_notifyurl}")
    private String pc_bond_refund_notify_url;

    /**
     * app回调地址
     */
    @Value("${weixin.app_notifyurl}")
    private String app_notify_url;

    @Value("${weixin.app_v3_notifyurl}")
    private String appV3NotifyUrl;

    /**
     * 商户秘钥
     */
    @Value("${weixin.key}")
    private String key;

    private final WXPayV3Util wxPayV3Util;

    @Override
    public Map<String, String> refund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        //APPV3版本的退款，以APPV3开头
        if (outRefundNo.startsWith("APPV3")) {
            return refundWithV3(transactionId, outRefundNo, total, refund);
        } else {
            return refundWithNormal(transactionId, outRefundNo, total, refund);
        }
    }

    private Map<String, String> refundWithV3(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        log.info(" orderRefund refundWithV3 {}, {}, {}, {}", transactionId, outRefundNo, total, refund);
        Map<String, Object> params = new HashMap<>();
        params.put("transaction_id", transactionId);
        params.put("out_refund_no", outRefundNo);
        params.put("notify_url", appV3NotifyUrl);
        params.put("amount", new HashMap<String, Object>() {
            {
                put("refund", refund.multiply(new BigDecimal(100)).intValue());
                put("total", total.multiply(new BigDecimal(100)).intValue());
                put("currency", "CNY");
            }
        });
        JSONObject obj = wxPayV3Util.doPostWeixinV3(WXPayV3Util.API_V_3_REFUND_URL, JSON.toJSONString(params));
        log.info(" orderRefund refundWithV3 result {}", JSON.toJSONString(obj));
        Map<String, String> result = new HashMap<>();
        if (obj.getString("status") != null && (WxPayEnum.REFUND_OK.getCode().equals(obj.getString("status")) || WxPayEnum.REFUND_PROCESSING.getCode().equals(obj.getString("status")))) {
            result.put("return_code", WxPayEnum.REFUND_OK.getCode());
            result.put("return_msg", WxPayEnum.REFUND_SUCCESS.getCode());
        } else if (WxPayEnum.NOT_ENOUGH_V3.getCode().equals(obj.getString("status"))) {
            result.put("return_code", WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode());
        }
        return result;
    }

    private Map<String, String> refundWithNormal(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        //退款资金来源-可用余额退款
        String refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS";
        Map<String, String> params = new HashMap<>();
        if (outRefundNo.contains("APP")) {
            //APP微信退款
            params.put("appid", app_appid);
        } else {
            params.put("appid", appid);
        }
        params.put("mch_id", mch_id);
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        //商户订单号和微信订单号二选一
//        params.put("out_trade_no", wxPayLog.getOutTradeNo());
        params.put("transaction_id", transactionId);
        params.put("out_refund_no", outRefundNo);
        params.put("total_fee", total.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_fee", refund.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_account", refundAccount);
        // 退款原因，若商户传入，会在下发给用户的退款消息中体现退款原因
        params.put("refund_desc", "退款");
        //退款回调
        params.put("notify_url", pc_refund_notify_url);
        //签名算法
        String sign = WXPayUtil.generateSignature(params, key);
        params.put("sign", sign);
        String xml = PaymentKit.toXml(params);
        log.info(xml);
        String xmlStr = WXPayUtil.doRefund("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, cert_url, mch_id);
        log.info(xmlStr);
        //加入微信支付日志
        return PaymentKit.xmlToMap(xmlStr);
    }

    @Override
    public Map<String, String> refundBond(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        //退款资金来源-可用余额退款
        String refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS";
        Map<String, String> params = new HashMap<>();
        if (outRefundNo.contains("APP")) {
            //APP微信退款
            params.put("appid", app_appid);
        } else {
            params.put("appid", appid);
        }
        params.put("mch_id", mch_id);
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        //商户订单号和微信订单号二选一
//        params.put("out_trade_no", wxPayLog.getOutTradeNo());
        params.put("transaction_id", transactionId);
        params.put("out_refund_no", outRefundNo);
        params.put("total_fee", total.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_fee", refund.multiply(BigDecimal.valueOf(100)).intValue() + "");
        params.put("refund_account", refundAccount);
        // 退款原因，若商户传入，会在下发给用户的退款消息中体现退款原因
        params.put("refund_desc", "退款");
        //退款回调
        params.put("notify_url", pc_bond_refund_notify_url);
        //签名算法
        String sign = WXPayUtil.generateSignature(params, key);
        params.put("sign", sign);
        String xml = PaymentKit.toXml(params);
        log.info(xml);
        String xmlStr = WXPayUtil.doRefund("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, cert_url, mch_id);
        log.info(xmlStr);
        //加入微信支付日志
        return PaymentKit.xmlToMap(xmlStr);
    }

}
