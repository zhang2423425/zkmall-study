/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.shop.impl;


import com.shop.zkmall.app.dao.shop.CereShopReturnDAO;
import com.shop.zkmall.app.service.shop.CereShopReturnService;
import com.shop.zkmall.commons.domain.shop.CereShopReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopReturnServiceImpl implements CereShopReturnService {

    private final CereShopReturnDAO cereShopReturnDAO;

    @Override
    public CereShopReturn findByShopId(Long shopId) {
        return cereShopReturnDAO.findByShopId(shopId);
    }
}
