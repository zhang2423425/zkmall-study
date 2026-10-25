/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.zkmall.business.dao.product.CereSkuNameDAO;
import com.shop.zkmall.business.param.product.DeleteSkuParam;
import com.shop.zkmall.business.param.product.SkuNameParam;
import com.shop.zkmall.business.param.product.SkuNameValueParam;
import com.shop.zkmall.business.param.product.SkuValueParam;
import com.shop.zkmall.business.service.product.CereSkuNameService;
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
public class CereSkuNameServiceImpl implements CereSkuNameService {

    private final CereSkuNameDAO cereSkuNameDAO;

    @Override
    public void insertBatch(List<CereSkuName> collect) {
        cereSkuNameDAO.insertBatch(collect);
    }

    @Override
    public void deleteBySkuId(Long skuId) {
        cereSkuNameDAO.deleteByPrimaryKey(skuId);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        cereSkuNameDAO.deleteByIds(ids);
    }

    @Override
    public void deleteByProductId(Long productId) {
        cereSkuNameDAO.deleteByProductId(productId);
    }

    @Override
    public List<SkuNameValueParam> findBySkuId(Long skuId) {
        return cereSkuNameDAO.findBySkuId(skuId);
    }

    @Override
    public List<SkuNameParam> findNameBySkuId(Long skuId) {
        return cereSkuNameDAO.findNameBySkuId(skuId);
    }

    @Override
    public List<SkuValueParam> findByNameAndSkuId(Long skuId, String skuName) {
        return cereSkuNameDAO.findByNameAndSkuId(skuId, skuName);
    }

    @Override
    public CereSkuName findValueBySkuId(Long skuId) {
        return cereSkuNameDAO.findValueBySkuId(skuId);
    }

    @Override
    public List<SkuNameParam> findNameByProductId(Long productId) {
        return cereSkuNameDAO.findNameByProductId(productId);
    }

    @Override
    public List<SkuValueParam> findByName(String skuName, Long productId) {
        return cereSkuNameDAO.findByName(skuName, productId);
    }

    @Override
    public CereSkuName findValueByProductId(Long productId) {
        return cereSkuNameDAO.findValueByProductId(productId);
    }

    @Override
    public void insert(CereSkuName cereSkuName) {
        cereSkuNameDAO.insert(cereSkuName);
    }

    @Override
    public List<Long> findDeleteSkuIds(List<DeleteSkuParam> deletes, Long productId) {
        return cereSkuNameDAO.findDeleteSkuIds(deletes, productId);
    }

    @Override
    public List<CereSkuName> findSkuNameListByProductId(Long productId) {
        return cereSkuNameDAO.findSkuNameListByProductId(productId);
    }

    @Override
    public List<CereSkuName> selectListBySkuIdList(List<Long> skuIdList) {
        LambdaQueryWrapper<CereSkuName> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CereSkuName::getSkuId, skuIdList);
        return cereSkuNameDAO.selectList(wrapper);
    }
}
