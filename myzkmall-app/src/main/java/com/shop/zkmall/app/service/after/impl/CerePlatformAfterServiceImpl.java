/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.after.impl;

import com.shop.zkmall.app.dao.after.CerePlatformAfterDAO;
import com.shop.zkmall.app.service.after.CerePlatformAfterService;
import com.shop.zkmall.commons.domain.after.CerePlatformAfter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformAfterServiceImpl implements CerePlatformAfterService {

    private final CerePlatformAfterDAO cerePlatformAfterDAO;

    @Override
    public void insert(CerePlatformAfter cerePlatformAfter) {
        cerePlatformAfterDAO.insert(cerePlatformAfter);
    }
}
