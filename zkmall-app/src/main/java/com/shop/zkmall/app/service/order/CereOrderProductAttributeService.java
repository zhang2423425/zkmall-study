/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order;

import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.OrderProductAttribute;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderProductAttributeService {

    void insertBatch(List<OrderProductAttribute> attributes);

    List<OrderProductAttribute> findBySkus(List<CartSku> skus);
}
