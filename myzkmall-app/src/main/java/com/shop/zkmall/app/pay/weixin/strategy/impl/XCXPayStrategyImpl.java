/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay.weixin.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.shop.zkmall.app.pay.weixin.strategy.WxPayStrategy;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.third.PaymentApi;
import com.shop.zkmall.commons.utils.third.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service(value = "xCXPayStrategyImpl")
public class XCXPayStrategyImpl implements WxPayStrategy {

    private static final String TYPE_FLAG = "XCX";

    private static final String TRADE_TYPE = "JSAPI";

    @Override
    public Map<String, String> prepay(String appid, String appAppid, String openid,
                                      String mchId, String appNotifyUrl, String redirectUrl,
                                      String ip, BigDecimal money, String orderFormId,
                                      String key) {
        try {
            Map<String, String> reqParams = WXPayUtil.prepareParam(mchId, appid, orderFormId,
                    money, ip, appNotifyUrl, openid, key, TYPE_FLAG, TRADE_TYPE);

            //调用支付定义下单API,返回预付单信息 prepay_id
            log.info(JSON.toJSONString(reqParams));
            String xmlResult = PaymentApi.pushOrder(reqParams);
            log.info(xmlResult);

            return WXPayUtil.assembleResult(xmlResult, reqParams.get("nonce_str"), appid, mchId, key);
        } catch (Exception e) {
            log.error(" pay fail XCXPayStrategyImpl {}", e.getMessage(), e);
            throw new CoBusinessException(CoReturnFormat.PAY_ORDER_ERROR);
        }
    }
}
