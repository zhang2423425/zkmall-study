/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.product;

import com.shop.zkmall.admin.page.product.Sku;
import com.shop.zkmall.admin.page.product.SkuNameParam;
import com.shop.zkmall.admin.page.product.SkuNameValueParam;
import com.shop.zkmall.admin.page.product.SkuValueParam;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.CereSkuName;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductSkuService {

    int findStockNumber(Long skuId);

    List<Sku> findByProductId(Long productId);

    List<SkuNameParam> findNameByProductId(Long productId);

    List<SkuValueParam> findByName(String skuName, Long productId);

    CereSkuName findValueByProductId(Long productId);

    List<SkuNameValueParam> findBySkuId(Long skuId);

    List<CereProductSku> selectListBySkuIdList(List<Long> collect);
}
