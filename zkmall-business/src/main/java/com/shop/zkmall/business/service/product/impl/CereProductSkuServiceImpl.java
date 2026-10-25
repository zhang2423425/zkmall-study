/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.zkmall.business.dao.product.CereProductSkuDAO;
import com.shop.zkmall.business.page.product.Sku;
import com.shop.zkmall.business.page.tool.ToolProduct;
import com.shop.zkmall.business.service.product.CereProductSkuService;
import com.shop.zkmall.commons.domain.product.CereProductSku;
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
    public void update(CereProductSku cereProductSku) {
        cereProductSkuDAO.updateByPrimaryKeySelective(cereProductSku);
    }

    @Override
    public void insertBatch(List<CereProductSku> adds) {
        cereProductSkuDAO.insertBatch(adds);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        cereProductSkuDAO.deleteByIds(ids);
    }

    @Override
    public void deleteByProductId(Long productId) {
        cereProductSkuDAO.deleteByProductId(productId);
    }

    @Override
    public List<Sku> findByProductId(Long productId) {
        return cereProductSkuDAO.findByProductId(productId);
    }

    @Override
    public Integer findVolumeByProductId(Long productId) {
        return cereProductSkuDAO.findVolumeByProductId(productId);
    }

    @Override
    public void insert(CereProductSku cereProductSku) {
        cereProductSkuDAO.insert(cereProductSku);
    }

    @Override
    public int findStockNumber(Long skuId) {
        return cereProductSkuDAO.findStockNumber(skuId);
    }

    @Override
    public List<ToolProduct> getToolSkus(Long productId) {
        return cereProductSkuDAO.getToolSkus(productId);
    }

    @Override
    public List<CereProductSku> findStockNumberByOrderId(Long orderId) {
        return cereProductSkuDAO.findStockNumberByOrderId(orderId);
    }

    @Override
    public List<CereProductSku> selectListBySkuIdList(List<Long> skuIdList) {
        return cereProductSkuDAO.selectBatchIds(skuIdList);
    }

    @Override
    public int findStockNumberBySkuId(Long skuId) {
        return cereProductSkuDAO.findStockNumberBySkuId(skuId);
    }

    @Override
    public void updateBatchSkus(List<CereProductSku> list) {
        cereProductSkuDAO.updateBatchSkus(list);
    }

    @Override
    public List<CereProductSku> selectStockNumberProductIdList(List<Long> productIdList) {
        return cereProductSkuDAO.selectStockNumberProductIdList(productIdList);
    }

    @Override
    public List<CereProductSku> selectListByProductId(Long productId) {
        LambdaQueryWrapper<CereProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CereProductSku::getProductId, productId);
        return cereProductSkuDAO.selectList(wrapper);
    }
}
