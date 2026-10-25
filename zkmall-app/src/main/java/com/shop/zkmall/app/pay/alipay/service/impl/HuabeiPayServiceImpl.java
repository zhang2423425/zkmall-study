/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shop.zkmall.app.pay.alipay.service.HuabeiPayService;
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
@Service(value = "huabeiPayService")
public class HuabeiPayServiceImpl implements HuabeiPayService {

    private final AlipayConfig alipayConfig;

    private final AlipayClient alipayClient;

    @Override
    public Map<String, String> gotoPay(String orderFormId, BigDecimal money, String openId, String ip, Integer type, Integer huabeiPeriod) {
        String outTradeNo;
        String subject = "通用下单";
        outTradeNo = orderFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "XCX";

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.create.
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setNotifyUrl(alipayConfig.getAppNotifyUrl());
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(money.toString());
        model.setSubject(subject);
        model.setBuyerId(openId);
        ExtendParams extendParams = new ExtendParams();
        extendParams.setHbFqNum(huabeiPeriod.toString());
        extendParams.setHbFqSellerPercent(alipayConfig.getHuabeiChargeType() == 1 ? "100" : "0");
        model.setExtendParams(extendParams);

        request.setBizModel(model);
        Map<String, String> result = new HashMap<>();

        try {
            AlipayTradeCreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                String trade_no = response.getTradeNo();
                result.put("tradeNo", trade_no);
            } else {
                throw new CoBusinessException(CoReturnFormat.PAY_ORDER_ERROR);
            }
        } catch (AlipayApiException e) {
            log.error(e.getMessage(), e);
            throw new CoBusinessException(CoReturnFormat.PAY_ORDER_ERROR);
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
    public Map<String, String> refundBond(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        return null;
    }

    @Override
    public String getCollectionCode(String orderFormid, BigDecimal money, String ip, String tradeType) throws Exception {
        return null;
    }

    @Override
    public String getOrderCollectionCode(String orderFormid, BigDecimal money, String ip, String tradeType) throws Exception {
        return null;
    }
}
