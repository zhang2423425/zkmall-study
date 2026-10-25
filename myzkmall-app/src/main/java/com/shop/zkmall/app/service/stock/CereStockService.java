/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.stock;

import com.shop.zkmall.app.page.product.ProductStockInfo;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereStockService {

    ProductStockInfo getActivityProductStock(Long productId, Integer activityType, Long activityId);

    ProductStockInfo getActivitySkuStock(Long skuId, Integer activityType, Long activityId);

    ProductStockInfo getSkuStockInfo(Long skuId);

    void updateActivityProductStock(Integer activityType, Long productId, Integer total, Integer stockNumber);

    void updateActivitySkuStock(Integer activityType, Long skuId, Integer total, Integer stockNumber);

    void updateSkuStock(Long skuId, Integer total, Integer stockNumber);

    void reduceStock(Integer activityType, Long activityId, Long productId, Long skuId, Integer buyNumber, Integer ifOversold);

    void rollbackStock(Integer activityType, Long activityId, Long productId, Long skuId, Integer buyNumber);

    void rollbackStockByOrderId(Long orderId);

    /**
     * 查询商家秒杀/商家折扣 对应sku的活动库存
     */
    ProductStockInfo getOrQueryShopActivitySkuStock(Integer activityType, Long activityId, Long skuId);

}
