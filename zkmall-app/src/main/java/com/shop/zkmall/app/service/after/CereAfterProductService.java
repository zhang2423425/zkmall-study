/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.after;

import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.param.after.AfterParam;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereAfterProductService {

    void insert(CereAfterProduct afterProduct);

    List<CereAfterProductAttribute> findValuesBySkuId(Long skuId);

    List<CartSku> findSkusByAfterId(Long afterId);

    List<CereAfterProduct> findSkuBySkus(AfterParam param);

    void deleteByAfterId(Long id);

    List<CereAfterProduct> findByOrderIdForCheck(Long orderId);
}
