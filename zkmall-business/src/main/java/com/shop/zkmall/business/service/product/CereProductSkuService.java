/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product;

import com.shop.zkmall.business.page.product.Sku;
import com.shop.zkmall.business.page.tool.ToolProduct;
import com.shop.zkmall.commons.domain.product.CereProductSku;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductSkuService {

    void update(CereProductSku cereProductSku);

    void insertBatch(List<CereProductSku> adds);

    void deleteByIds(List<Long> ids);

    void deleteByProductId(Long productId);

    List<Sku> findByProductId(Long productId);

    Integer findVolumeByProductId(Long productId);

    void insert(CereProductSku cereProductSku);

    int findStockNumber(Long skuId);

    List<ToolProduct> getToolSkus(Long productId);

    List<CereProductSku> findStockNumberByOrderId(Long orderId);

    List<CereProductSku> selectListBySkuIdList(List<Long> skuIdList);

    int findStockNumberBySkuId(Long skuId);

    void updateBatchSkus(List<CereProductSku> productSkus);

    List<CereProductSku> selectStockNumberProductIdList(List<Long> productIdList);

    List<CereProductSku> selectListByProductId(Long productId);
}
