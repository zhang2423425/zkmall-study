/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.cart;

import com.shop.zkmall.commons.domain.cart.CereCartAttribute;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereCartAttributeService {

    void insertBatch(List<CereCartAttribute> attributes);

    List<CereCartAttribute> selectCartSkuValueGroup(List<Long> cartIdList);
}
