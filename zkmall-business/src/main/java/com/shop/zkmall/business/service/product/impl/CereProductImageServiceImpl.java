/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product.impl;

import com.shop.zkmall.business.dao.product.CereProductImageDAO;
import com.shop.zkmall.business.service.product.CereProductImageService;
import com.shop.zkmall.commons.domain.product.CereProductImage;
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
public class CereProductImageServiceImpl implements CereProductImageService {

    private final CereProductImageDAO cereProductImageDAO;

    @Override
    public void insertBatch(List<CereProductImage> list) {
        cereProductImageDAO.insertBatch(list);
    }

    @Override
    public void deleteByProductId(Long productId) {
        cereProductImageDAO.deleteByProductId(productId);
    }

    @Override
    public List<String> findByProductId(Long productId) {
        return cereProductImageDAO.findByProductId(productId);
    }

    @Override
    public List<CereProductImage> selectListByProductId(Long productId) {
        return cereProductImageDAO.selectListByProductId(productId);
    }
}
