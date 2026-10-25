/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.canvas;

import com.shop.zkmall.admin.param.canvas.CanvasPageParam;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasCustom;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformCanvasCustomService {

    /**
     * 新增或更新自定义页面
     */
    void saveCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user);

    /**
     * 删除自定义页面
     */
    void delCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user);

    /**
     * 分页查询自定义页面列表
     */
    Page<CerePlatformCanvasCustom> selectCanvasCustomList(CanvasPageParam param);
}
