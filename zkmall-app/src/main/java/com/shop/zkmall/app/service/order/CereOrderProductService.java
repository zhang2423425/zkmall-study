/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order;

import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderProductService {

    void insert(CereOrderProduct orderProduct);

    List<CartSku> findOrderProductSku(Long orderId);

    List<CereOrderProduct> findByOrderIds(List<Long> orderIdList);

    List<CartSku> findProductStatsByOrderIds(List<Long> orderIdList);
}
