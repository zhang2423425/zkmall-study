/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.business.impl;

import com.shop.zkmall.admin.dao.business.CerePlatformBusinessDAO;
import com.shop.zkmall.admin.service.business.CerePlatformBusinessService;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformBusinessServiceImpl implements CerePlatformBusinessService {

    private final CerePlatformBusinessDAO cerePlatformBusinessDAO;

    @Override
    public void insert(CerePlatformBusiness cerePlatformBusiness) {
        cerePlatformBusinessDAO.insert(cerePlatformBusiness);
    }

    @Override
    public void update(CerePlatformBusiness cerePlatformBusiness) {
        cerePlatformBusinessDAO.updateByPrimaryKeySelective(cerePlatformBusiness);
    }

    @Override
    public CerePlatformBusiness findByPhone(String phone) {
        return cerePlatformBusinessDAO.findByPhone(phone);
    }
}
