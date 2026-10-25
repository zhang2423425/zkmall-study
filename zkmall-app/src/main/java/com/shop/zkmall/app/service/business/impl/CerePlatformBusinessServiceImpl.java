/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.business.impl;

import com.shop.zkmall.app.dao.business.CerePlatformBusinessDAO;
import com.shop.zkmall.app.service.business.CerePlatformBusinessService;
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
}
