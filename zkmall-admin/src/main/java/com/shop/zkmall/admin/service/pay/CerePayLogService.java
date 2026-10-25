/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.pay;

import com.shop.zkmall.admin.page.after.PayLog;
import com.shop.zkmall.commons.domain.pay.CerePayLog;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePayLogService {

    void insert(CerePayLog payLog);

    PayLog findByOrderFormid(String orderFormid);

    void update(CerePayLog payLog);
}
