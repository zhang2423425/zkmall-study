/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product;

import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.product.ProductSkus;
import com.shop.zkmall.app.page.product.ProductStockInfo;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.Sku;
import com.shop.zkmall.commons.domain.product.SkuName;
import com.shop.zkmall.commons.domain.product.SkuValue;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductSkuService {

    List<SkuName> findSkuNames(Long productId);

    List<SkuValue> findSkuValues(String skuName, Long productId);

    ProductSkus findSku(Long skuId);

    ProductSkus findFirstSku(Long productId);

    List<Sku> findSimpleSkuByProductId(Long productId);

    List<Sku> findSkuByProductId(Long productId);

    String findValueBySkuId(Long skuId);

    Integer findStockNumber(Long skuId);

    List<CartSku> findStockNumberBySkus(List<ProductSku> skus);

    void updateBatch(List<CartSku> productSkus);

    List<CartSku> findSkuByOrderId(Long orderId);

    List<CartSku> findSkuByIds(List<Long> ids, Long orderId);

    List<CereProductSku> findAll();

    List<SkuValue> findOneValues(Long skuId);

    List<SkuValue> findValuesByProductId(Long productId);

    ProductStockInfo findProductStockInfo(Long productId);

    ProductStockInfo findSkuStockInfo(Long skuId);

    int updateStockNumber(Long skuId, int stockNumber);

    int rollbackStock(Long skuId, Integer buyNumber);

    List<SkuValue> findSkuValueBySkuId(String skuName, Long skuId);

    List<Sku> findStockNumberByProductId(Long productId);

    List<CartSku> findStockNumberBySkusForRealInfo(List<ProductSku> skus);

    Long totalStockNumber(Long productId);
}
