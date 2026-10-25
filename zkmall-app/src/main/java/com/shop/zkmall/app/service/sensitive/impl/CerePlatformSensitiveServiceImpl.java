/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.sensitive.impl;

import com.shop.zkmall.app.dao.sensitive.CerePlatformSensitiveDAO;
import com.shop.zkmall.app.service.sensitive.CerePlatformSensitiveService;
import com.shop.zkmall.commons.domain.sensitive.CerePlatformSensitive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformSensitiveServiceImpl implements CerePlatformSensitiveService {

    private final CerePlatformSensitiveDAO cerePlatformSensitiveDAO;

    @Override
    public CerePlatformSensitive findSensitive() {
        return cerePlatformSensitiveDAO.findSensitive();
    }
}
