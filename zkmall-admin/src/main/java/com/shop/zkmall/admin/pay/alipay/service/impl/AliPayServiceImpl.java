/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.pay.alipay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.shop.zkmall.admin.pay.alipay.service.AliPayService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.WxPayEnum;
import com.shop.zkmall.commons.exception.CoBusinessException;
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
public class AliPayServiceImpl implements AliPayService {

    private final AlipayClient alipayClient;

    @Override
    public Map<String, String> refund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception {
        Map<String, String> result = new HashMap<>();

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //request.setNotifyUrl(AlipayConfig.REFUND_NOTIFY_URL);
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        //model.setOutTradeNo(outTradeNo);
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
        Map<String, String> result = new HashMap<>();

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //request.setNotifyUrl(AlipayConfig.REFUND_NOTIFY_URL);
        AlipayTradeRefundApplyModel model = new AlipayTradeRefundApplyModel();
        //model.setOutTradeNo(outTradeNo);
        model.setTradeNo(transactionId);
        model.setRefundAmount(refund.toString());
        request.setBizModel(model);

        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            //这里只是申请退款，兼容微信的返回
            result.put("return_msg", WxPayEnum.REFUND_SUCCESS.getCode());
            result.put("return_code", WxPayEnum.REFUND_OK.getCode());
            result.put("out_trade_no", response.getOutTradeNo());
            result.put("transaction_id", response.getTradeNo());
        } else {
            log.error("alipay refundBond failed: subCode = {}, subMsg = {}", response.getSubCode(), response.getSubMsg());
            throw new CoBusinessException(CoReturnFormat.APPLY_REFUND_FAILED);
        }
        return result;
    }

}
