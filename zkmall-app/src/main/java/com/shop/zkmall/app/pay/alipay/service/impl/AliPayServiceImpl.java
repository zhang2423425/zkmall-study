/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shop.zkmall.app.pay.alipay.service.AliPayService;
import com.shop.zkmall.commons.config.AlipayConfig;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.WxPayEnum;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RequiredArgsConstructor
@Service(value = "aliPayService")
public class AliPayServiceImpl implements AliPayService {

    private final AlipayClient alipayClient;

    private final AlipayConfig alipayConfig;

    @Override
    public Map<String, String> gotoPay(String orderFormId, BigDecimal money, String openId, String ip, Integer type, Integer huabeiPeriod) {
        String outTradeNo;
        String subject = "通用下单";
        outTradeNo = orderFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "XCX";

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.create.
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setNotifyUrl(alipayConfig.getAppNotifyUrl());
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。
        /*request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +// 可以随机生成订单号：String outTradeNo = UUID.randomUUID().toString().replace("-", "");
                "\"total_amount\":" + money.toString() + "," +
                "\"extend_params\":" + money.toString() + "," +
                "\"subject\":\"" + subject +"\"," +
                "\"buyer_id\":\"" + openId + "\"" + // 小程序支付场景中该参数必传
                "}");*/
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(money.toString());
        model.setSubject(subject);
        model.setBuyerId(openId);

        request.setBizModel(model);
        Map<String, String> result = new HashMap<>();

        try {
            AlipayTradeCreateResponse response = alipayClient.execute(request);
            String tradeNo = response.getTradeNo();
            result.put("tradeNo", tradeNo);
        } catch (AlipayApiException e) {
            log.error("下单错误:", e);
        }

        return result;
    }

    @Override
    public Map<String, String> refund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> orderRefund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        Map<String, String> result = new HashMap<>();

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(alipayConfig.getRefundNotifyUrl());
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        model.setTradeNo(transactionId);
        model.setRefundAmount(refund.toString());
        request.setBizModel(model);

        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            //这里只是申请退款，兼容微信的返回
            result.put("return_msg", WxPayEnum.REFUND_SUCCESS.getCode());
            result.put("return_code", WxPayEnum.REFUND_OK.getCode());
        } else {
            log.error("alipay refund failed: subCode = {}, subMsg = {}", response.getSubCode(), response.getSubMsg());
            throw new CoBusinessException(CoReturnFormat.APPLY_REFUND_FAILED);
        }
        return result;
    }

    @Override
    public Map<String, String> refundBond(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) {
        return null;
    }

    @Override
    public String getCollectionCode(String orderFormId, BigDecimal money, String ip, String tradeType) {
        return null;
    }

    @Override
    public String getOrderCollectionCode(String orderFormId, BigDecimal money, String ip, String tradeType) throws Exception {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayConfig.getAppNotifyUrl());
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        //生成一个新的订单支付编号
        String outTradeNo = orderFormId + "-" + RandomStringUtil.getRandomCode(3, 0);
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(money.toString());
        model.setSubject("商品支付");
        request.setBizModel(model);

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("alipay result {} {} {} {} {} {}", response.getCode(), response.getSubCode(), response.getMsg(), response.getSubMsg(), response.getOutTradeNo(), response.getBody());
            return response.getQrCode();
        } else {
            log.error("alipay getCollectionCode failed: subCode = {}, subMsg = {}", response.getSubCode(), response.getSubMsg());
            throw new CoBusinessException(CoReturnFormat.GENERATE_COLLECTION_CODE_FAILED);
        }
    }

    @Override
    public String getPayForm(String orderFormId, BigDecimal money, String ip) {
        String outTradeNo = "";
        String subject = "通用下单";
        outTradeNo = orderFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "PC";

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.create.
        //AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        //request.setNotifyUrl(AlipayConfig.APP_NOTIFY_URL);
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。
        /*request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +// 可以随机生成订单号：String outTradeNo = UUID.randomUUID().toString().replace("-", "");
                "\"total_amount\":" + money.toString() + "," +
                "\"extend_params\":" + money.toString() + "," +
                "\"subject\":\"" + subject +"\"," +
                "\"buyer_id\":\"" + openId + "\"" + // 小程序支付场景中该参数必传
                "}");*/
        //AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        //model.setOutTradeNo(outTradeNo);
        //model.setTotalAmount(money.toString());
        //model.setSubject(subject);
        //model.setBuyerId(openId);

        //request.setBizModel(model);
        Map<String, String> result = new HashMap<>();

        try {

            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();

            // 设置商户订单号
            model.setOutTradeNo(outTradeNo);

            // 设置订单总金额
            model.setTotalAmount(money.toString());

            // 设置订单标题
            model.setSubject(subject);

            // 设置产品码
            model.setProductCode("FAST_INSTANT_TRADE_PAY");

            // 设置PC扫码支付的方式
            model.setQrPayMode("1");

            // 设置商户自定义二维码宽度
            model.setQrcodeWidth(100L);

            // 设置订单绝对超时时间
            //model.setTimeExpire("2016-12-31 10:05:01");


            // 设置商户传入业务信息
            model.setBusinessParams("{\"mc_create_trade_ip\":\"" + ip + "\"}");


            // 设置请求后页面的集成方式
            model.setIntegrationType("PCWEB");

            // 设置请求来源地址
            //model.setRequestFromUrl("https://");

            // 设置商户门店编号
            //model.setStoreId("NJ_001");

            // 设置商户的原始订单号
            model.setMerchantOrderNo(orderFormId);

            request.setBizModel(model);
            // 第三方代调用模式下请设置app_auth_token
            // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            // 如果需要返回GET请求，请使用
            // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
            String pageRedirectionData = response.getBody();
            System.out.println("   -------------------pageRedirectionData--------------");
            System.out.println(pageRedirectionData);

            if (response.isSuccess()) {
                System.out.println("调用成功");
                return pageRedirectionData;
            } else {
                System.out.println("调用失败");
                throw new CoBusinessException("创建支付单失败，请联系管理员");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                // System.out.println(diagnosisUrl);
            }

        } catch (AlipayApiException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
