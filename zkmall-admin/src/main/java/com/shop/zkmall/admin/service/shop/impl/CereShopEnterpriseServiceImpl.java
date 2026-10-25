/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop.impl;

import com.shop.zkmall.admin.dao.shop.CereShopEnterpriseDAO;
import com.shop.zkmall.admin.service.shop.CereShopEnterpriseService;
import com.shop.zkmall.commons.domain.shop.CereShopEnterprise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopEnterpriseServiceImpl implements CereShopEnterpriseService {

    private final CereShopEnterpriseDAO cereShopEnterpriseDAO;

    @Override
    public CereShopEnterprise findByShopId(Long shopId) {
        return cereShopEnterpriseDAO.findByShopId(shopId);
    }
}
