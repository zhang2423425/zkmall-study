/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.pay.alipay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shop.zkmall.business.pay.alipay.service.HuabeiPayService;
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
@Service
@RequiredArgsConstructor
public class HuabeiPayServiceImpl implements HuabeiPayService {

    private final AlipayConfig alipayConfig;

    private final AlipayClient alipayClient;

    @Override
    public Map<String, String> refund(String outTradeNo, String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund, Long afterId) throws Exception {
        Map<String, String> result = new HashMap<>();

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(alipayConfig.getRefundNotifyUrl());
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        model.setOutTradeNo(outTradeNo);
        if (!total.equals(refund)) {
            //这里不使用outRefundNo是因为，outRefundNo针对一个订单只生成一次，多次退款，这个id无法保证唯一
            model.setOutRequestNo(afterId + "");
        }
        //model.setTradeNo(transactionId);
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
    public String getCollectionCode(String orderFormid, BigDecimal money, String ip, String tradeType) throws Exception {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayConfig.getBondNotifyUrl());
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        //生成一个新的订单支付编号
        String outTradeNo = orderFormid + "-" + RandomStringUtil.getRandomCode(3, 0);
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(money.toString());
        model.setSubject("生成保证金收款码");
        request.setBizModel(model);

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return response.getQrCode();
        } else {
            log.error("alipay getCollectionCode failed: subCode = {}, subMsg = {}", response.getSubCode(), response.getSubMsg());
            throw new CoBusinessException(CoReturnFormat.GENERATE_COLLECTION_CODE_FAILED);
        }
    }

    @Override
    public Map<String, String> orderRefund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        return null;
    }
}
