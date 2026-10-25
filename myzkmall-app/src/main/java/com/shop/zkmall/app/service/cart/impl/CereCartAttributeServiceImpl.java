/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.cart.impl;

import com.shop.zkmall.app.dao.cart.CereCartAttributeDAO;
import com.shop.zkmall.app.service.cart.CereCartAttributeService;
import com.shop.zkmall.commons.domain.cart.CereCartAttribute;
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
public class CereCartAttributeServiceImpl implements CereCartAttributeService {

    private final CereCartAttributeDAO cereCartAttributeDAO;

    @Override
    public void insertBatch(List<CereCartAttribute> attributes) {
        cereCartAttributeDAO.insertBatch(attributes);
    }

    @Override
    public List<CereCartAttribute> selectCartSkuValueGroup(List<Long> cartIdList) {
        return cereCartAttributeDAO.selectCartSkuValueGroup(cartIdList);
    }
}
