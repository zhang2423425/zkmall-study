/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay.weixin.strategy;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface WxPayStrategy {

    Map<String, String> prepay(String appid, String appAppid, String openid,
                               String mchId, String appNotifyUrl, String redirectUrl,
                               String ip, BigDecimal money, String orderFormId,
                               String key);

}
