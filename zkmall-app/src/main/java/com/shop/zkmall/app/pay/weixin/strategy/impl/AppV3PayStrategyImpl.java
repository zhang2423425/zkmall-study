/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay.weixin.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.app.pay.weixin.strategy.WxPayStrategy;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.RandomStringUtil;
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
public class AppV3PayStrategyImpl implements WxPayStrategy {

    private final WXPayV3Util wxPayV3Util;

    @Value("${weixin.app_v3_notifyurl}")
    private String appV3NotifyUrl;

    @Override
    public Map<String, String> prepay(String appid, String appAppid, String openid,
                                      String mchId, String appNotifyUrl, String redirectUrl,
                                      String ip, BigDecimal money, String orderFormId,
                                      String key) {
        try {
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put("mchid", mchId);
            String outTradeNo = orderFormId + "-" + RandomStringUtil.getRandomCode(3, 0) + "APPV3";
            reqParams.put("out_trade_no", outTradeNo);
            reqParams.put("appid", appAppid);
            reqParams.put("description", "充值" + orderFormId + "订单-" + "APPV3");
            reqParams.put("notify_url", appV3NotifyUrl);
            reqParams.put("amount", new HashMap<String, Object>() {
                {
                    put("total", money.multiply(BigDecimal.valueOf(100)).intValue());
                    put("currency", "CNY");
                }
            });

            log.info("appV3PayStrategyImpl {}", JSON.toJSONString(reqParams));
            JSONObject result = wxPayV3Util.doPostWeixinV3(WXPayV3Util.API_V_3_PLACE_AN_ORDER_URL, JSONUtil.toJsonStr(reqParams));
            log.info("appV3PayStrategyImpl {}", result.toJSONString());

            if (ObjectUtil.isNull(result.get("prepay_id"))) {
                throw new CoBusinessException("微信支付下单失败，请检查配置");
            }

            String prepayId = (String) result.get("prepay_id");
            // 获取app拉起支付签名
            String nonceStr = System.currentTimeMillis() / 1000 + "";
            long timestamp = System.currentTimeMillis() / 1000;
            String sign;
            try {
                sign = wxPayV3Util.sign(appAppid, timestamp, nonceStr, prepayId);
            } catch (Exception e) {
                log.error(" sign fail AppV3PayStrategyImpl {}", e.getMessage(), e);
                throw new CoBusinessException("生成签名错误：" + e.getMessage());
            }
            log.info("sign____________________{}", sign);
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("appId", appAppid);
            returnMap.put("partnerId", mchId);
            returnMap.put("prepayId", prepayId);
            returnMap.put("packages", "Sign=WXPay");
            returnMap.put("nonceStr", nonceStr);
            returnMap.put("timeStamp", String.valueOf(timestamp));
            returnMap.put("paySign", sign);
            log.info("returnMap________{}", returnMap);
            return returnMap;
        } catch (Exception e) {
            log.error(" pay fail AppV3PayStrategyImpl {}", e.getMessage(), e);
            throw new CoBusinessException(CoReturnFormat.PAY_ORDER_ERROR);
        }
    }
}
