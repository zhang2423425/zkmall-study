/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.canvas;

import com.shop.zkmall.admin.param.canvas.CanvasPageParam;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformCanvasService {

    void saveCanvas(CerePlatformCanvas canvas, CerePlatformUser user);

    CerePlatformCanvas getCanvas(CerePlatformCanvas canvas);

    void delCanvas(CerePlatformCanvas canvas, CerePlatformUser user);

    Page<CerePlatformCanvas> selectCanvasList(CanvasPageParam param);
}
