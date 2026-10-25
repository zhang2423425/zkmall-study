/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.shop;

import com.shop.zkmall.commons.domain.shop.CereShopVisit;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopVisitService {

    void insert(CereShopVisit visit);

    void updateBuyerData(Long buyerUserId, Long id);
}
