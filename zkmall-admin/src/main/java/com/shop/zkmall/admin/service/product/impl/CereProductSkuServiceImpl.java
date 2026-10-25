/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.product.impl;

import com.shop.zkmall.admin.dao.product.CereProductSkuDAO;
import com.shop.zkmall.admin.page.product.Sku;
import com.shop.zkmall.admin.page.product.SkuNameParam;
import com.shop.zkmall.admin.page.product.SkuNameValueParam;
import com.shop.zkmall.admin.page.product.SkuValueParam;
import com.shop.zkmall.admin.service.product.CereProductSkuService;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.CereSkuName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereProductSkuServiceImpl implements CereProductSkuService {

    private final CereProductSkuDAO cereProductSkuDAO;

    @Override
    public int findStockNumber(Long skuId) {
        return cereProductSkuDAO.findStockNumber(skuId);
    }

    @Override
    public List<Sku> findByProductId(Long productId) {
        return cereProductSkuDAO.findByProductId(productId);
    }

    @Override
    public List<SkuNameParam> findNameByProductId(Long productId) {
        return cereProductSkuDAO.findNameByProductId(productId);
    }

    @Override
    public List<SkuValueParam> findByName(String skuName, Long productId) {
        return cereProductSkuDAO.findByName(skuName, productId);
    }

    @Override
    public CereSkuName findValueByProductId(Long productId) {
        return cereProductSkuDAO.findValueByProductId(productId);
    }

    @Override
    public List<SkuNameValueParam> findBySkuId(Long skuId) {
        return cereProductSkuDAO.findBySkuId(skuId);
    }

    @Override
    public List<CereProductSku> selectListBySkuIdList(List<Long> skuIdList) {
        return cereProductSkuDAO.selectBatchIds(skuIdList);
    }
}
