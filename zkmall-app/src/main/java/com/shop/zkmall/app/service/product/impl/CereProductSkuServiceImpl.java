/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product.impl;

import com.shop.zkmall.app.dao.product.CereProductSkuDAO;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.product.ProductSkus;
import com.shop.zkmall.app.page.product.ProductStockInfo;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.commons.domain.product.CereProductSku;
import com.shop.zkmall.commons.domain.product.Sku;
import com.shop.zkmall.commons.domain.product.SkuName;
import com.shop.zkmall.commons.domain.product.SkuValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereProductSkuServiceImpl implements CereProductSkuService {

    private final CereProductSkuDAO cereProductSkuDAO;

    @Override
    public List<SkuName> findSkuNames(Long productId) {
        return cereProductSkuDAO.findSkuNames(productId);
    }

    @Override
    public List<SkuValue> findSkuValues(String skuName, Long productId) {
        return cereProductSkuDAO.findSkuValues(skuName, productId);
    }

    @Override
    public ProductSkus findSku(Long skuId) {
        return cereProductSkuDAO.findSku(skuId);
    }

    @Override
    public ProductSkus findFirstSku(Long productId) {
        return cereProductSkuDAO.findFirstSku(productId);
    }

    @Override
    public List<Sku> findSimpleSkuByProductId(Long productId) {
        return cereProductSkuDAO.findSimpleSkuByProductId(productId);
    }

    @Override
    public List<Sku> findSkuByProductId(Long productId) {
        return cereProductSkuDAO.findSkuByProductId(productId);
    }

    @Override
    public String findValueBySkuId(Long skuId) {
        return cereProductSkuDAO.findValueBySkuId(skuId);
    }

    @Override
    public Integer findStockNumber(Long skuId) {
        return cereProductSkuDAO.findStockNumber(skuId);
    }

    @Override
    public List<CartSku> findStockNumberBySkus(List<ProductSku> skus) {
        return cereProductSkuDAO.findStockNumberBySkus(skus);
    }

    @Override
    public void updateBatch(List<CartSku> productSkus) {
        cereProductSkuDAO.updateBatch(productSkus);
    }


    @Override
    public List<CartSku> findSkuByOrderId(Long orderId) {
        return cereProductSkuDAO.findSkuByOrderId(orderId);
    }

    @Override
    public List<CartSku> findSkuByIds(List<Long> ids, Long orderId) {
        return cereProductSkuDAO.findSkuByIds(ids, orderId);
    }

    @Override
    public List<CereProductSku> findAll() {
        return cereProductSkuDAO.findAll();
    }

    @Override
    public List<SkuValue> findOneValues(Long skuId) {
        return cereProductSkuDAO.findOneValues(skuId);
    }


    @Override
    public List<SkuValue> findValuesByProductId(Long productId) {
        return cereProductSkuDAO.findValuesByProductId(productId);
    }

    @Override
    public ProductStockInfo findProductStockInfo(Long productId) {
        return cereProductSkuDAO.findProductStockInfo(productId);
    }

    @Override
    public ProductStockInfo findSkuStockInfo(Long skuId) {
        return cereProductSkuDAO.findSkuStockInfo(skuId);
    }

    @Override
    public int updateStockNumber(Long skuId, int stockNumber) {
        CereProductSku sku = new CereProductSku();
        sku.setSkuId(skuId);
        sku.setStockNumber(stockNumber);
        return cereProductSkuDAO.updateByPrimaryKeySelective(sku);
    }

    @Override
    public int rollbackStock(Long skuId, Integer buyNumber) {
        return cereProductSkuDAO.rollbackStock(skuId, buyNumber);
    }

    @Override
    public List<SkuValue> findSkuValueBySkuId(String skuName, Long skuId) {
        return cereProductSkuDAO.findSkuValueBySkuId(skuName, skuId);
    }

    @Override
    public List<Sku> findStockNumberByProductId(Long productId) {
        return cereProductSkuDAO.findStockNumberByProductId(productId);
    }

    @Override
    public List<CartSku> findStockNumberBySkusForRealInfo(List<ProductSku> skus) {
        List<Long> skuIdList = skus.stream().map(ProductSku::getSkuId).collect(Collectors.toList());
        return cereProductSkuDAO.findCartSkuList(skuIdList);
    }

    @Override
    public Long totalStockNumber(Long productId) {
        return cereProductSkuDAO.totalStockNumber(productId);
    }
}
