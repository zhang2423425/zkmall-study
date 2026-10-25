/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.order;

import com.shop.zkmall.business.param.after.AfterIdParam;
import com.shop.zkmall.business.param.order.OrderDileveryParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderDileverService {

    void dilevery(OrderDileveryParam param, CerePlatformBusiness user) throws Exception;

    void refundDilevery(AfterIdParam param, CerePlatformBusiness user);
}
