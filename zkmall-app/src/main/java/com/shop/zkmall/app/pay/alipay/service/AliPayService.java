/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay.alipay.service;

import com.shop.zkmall.app.pay.PayService;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface AliPayService extends PayService {

    String getPayForm(String orderFormId, BigDecimal money, String ip);

}
