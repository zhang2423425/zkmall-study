/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.canvas.impl;

import com.shop.zkmall.app.dao.canvas.CerePlatformCanvasPageDAO;
import com.shop.zkmall.app.service.canvas.CerePlatformCanvasPageService;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformCanvasPageServiceImpl implements CerePlatformCanvasPageService {

    private final CerePlatformCanvasPageDAO cerePlatformCanvasPageDAO;

    @Override
    public CerePlatformCanvasPage getHomePage(CerePlatformCanvasPage page) {
        page.setHomePageFlag(true);
        return cerePlatformCanvasPageDAO.getCanvas(page);
    }
}
