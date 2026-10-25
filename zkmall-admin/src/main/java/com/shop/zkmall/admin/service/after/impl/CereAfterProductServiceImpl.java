/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.after.impl;

import com.shop.zkmall.admin.dao.after.CereAfterProductDAO;
import com.shop.zkmall.admin.service.after.CereAfterProductService;
import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;
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
public class CereAfterProductServiceImpl implements CereAfterProductService {

    private final CereAfterProductDAO cereAfterProductDAO;

    @Override
    public void insert(CereAfterProduct afterProduct) {
        cereAfterProductDAO.insert(afterProduct);
    }

    @Override
    public void insertBatchAttibutes(List<CereAfterProductAttribute> attributes) {
        cereAfterProductDAO.insertBatchAttibutes(attributes);
    }
}
