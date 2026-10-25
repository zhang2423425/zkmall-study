/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.business.impl;

import com.shop.zkmall.business.dao.business.CereBusinessShopDAO;
import com.shop.zkmall.business.service.business.CereBusinessShopService;
import com.shop.zkmall.commons.domain.business.CereBusinessShop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBusinessShopServiceImpl implements CereBusinessShopService {

    private final CereBusinessShopDAO cereBusinessShopDAO;

    @Override
    public void insert(CereBusinessShop cereBusinessShop) {
        cereBusinessShopDAO.insert(cereBusinessShop);
    }

    @Override
    public void deleteByUserId(Long businessUserId) {
        cereBusinessShopDAO.deleteByUserId(businessUserId);
    }

    @Override
    public Long findByShopId(Long shopId) {
        return cereBusinessShopDAO.findByShopId(shopId);
    }
}
