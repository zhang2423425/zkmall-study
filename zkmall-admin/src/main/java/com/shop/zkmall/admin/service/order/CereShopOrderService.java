/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.order;

import com.shop.zkmall.admin.page.order.ShopOrder;
import com.shop.zkmall.admin.param.order.OrderGetAllParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.domain.product.CereProductSku;

import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopOrderService {

    void updateAfterState(CereShopOrder cereShopOrder);

    void insert(CereShopOrder order);

    void updateState(CereShopOrder cereShopOrder);

    void updateBatchStock(List<CereProductSku> skus);

    Page<ShopOrder> getAll(OrderGetAllParam param);

    ShopOrder getById(Long orderId);

    Map<Long, Integer> selectSalesVolumeBySkuIdList(List<Long> skuIdList);
}
