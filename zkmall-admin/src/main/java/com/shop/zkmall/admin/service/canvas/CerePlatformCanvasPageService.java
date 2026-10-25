/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.canvas;

import com.shop.zkmall.admin.param.canvas.CanvasPageQueryParam;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasPage;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformCanvasPageService {

    void saveCanvasPage(CerePlatformCanvasPage canvas, CerePlatformUser user);

    void delCanvasPage(CerePlatformCanvasPage canvas, CerePlatformUser user);

    Page<CerePlatformCanvasPage> getAll(CanvasPageQueryParam param);

    CerePlatformCanvasPage getCanvas(CanvasPageQueryParam canvas);

    int settingHomePage(CanvasPageQueryParam param, CerePlatformUser user);

    List<CerePlatformCanvasPage> getTemplatePage(CanvasPageQueryParam param);
}
