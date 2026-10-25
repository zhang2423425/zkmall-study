/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product;

import com.shop.zkmall.business.param.product.DeleteSkuParam;
import com.shop.zkmall.business.param.product.SkuNameParam;
import com.shop.zkmall.business.param.product.SkuNameValueParam;
import com.shop.zkmall.business.param.product.SkuValueParam;
import com.shop.zkmall.commons.domain.product.CereSkuName;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereSkuNameService {

    void insertBatch(List<CereSkuName> collect);

    void deleteBySkuId(Long skuId);

    void deleteByIds(List<Long> ids);

    void deleteByProductId(Long productId);

    List<SkuNameValueParam> findBySkuId(Long skuId);

    List<SkuNameParam> findNameBySkuId(Long skuId);

    List<SkuValueParam> findByNameAndSkuId(Long skuId, String skuName);

    CereSkuName findValueBySkuId(Long skuId);

    List<SkuNameParam> findNameByProductId(Long productId);

    List<SkuValueParam> findByName(String skuName, Long productId);

    CereSkuName findValueByProductId(Long productId);

    void insert(CereSkuName cereSkuName);

    List<Long> findDeleteSkuIds(List<DeleteSkuParam> deletes, Long productId);

    List<CereSkuName> findSkuNameListByProductId(Long productId);

    List<CereSkuName> selectListBySkuIdList(List<Long> skuIdList);
}
