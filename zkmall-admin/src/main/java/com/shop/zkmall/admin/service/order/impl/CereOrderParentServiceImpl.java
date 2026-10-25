/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.order.impl;

import com.shop.zkmall.admin.dao.order.CereOrderParentDAO;
import com.shop.zkmall.admin.service.order.CereOrderParentService;
import com.shop.zkmall.commons.domain.order.CereOrderParent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderParentServiceImpl implements CereOrderParentService {

    private final CereOrderParentDAO cereOrderParentDAO;

    @Override
    public void insert(CereOrderParent parent) {
        cereOrderParentDAO.insert(parent);
    }
}
