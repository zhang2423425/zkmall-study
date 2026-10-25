/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.order.impl;

import com.shop.zkmall.admin.dao.order.CereOrderProductDAO;
import com.shop.zkmall.admin.service.order.CereOrderProductService;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.domain.order.CereOrderProductAttribute;
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
public class CereOrderProductServiceImpl implements CereOrderProductService {

    private final CereOrderProductDAO cereOrderProductDAO;

    @Override
    public void insert(CereOrderProduct orderProduct) {
        cereOrderProductDAO.insert(orderProduct);
    }

    @Override
    public void insertBatchAttibutes(List<CereOrderProductAttribute> attributes) {
        cereOrderProductDAO.insertBatchAttibutes(attributes);
    }
}
