/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.canvas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasPage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformCanvasPageDAO extends BaseMapper<CerePlatformCanvasPage> {

    CerePlatformCanvasPage selectByPrimaryKey(Long canvasId);

    CerePlatformCanvasPage getCanvas(CerePlatformCanvasPage canvas);

    //List<CerePlatformCanvasPage> selectCanvasList(CanvasPageQueryParam com.shop.zkmall.admin.param);

}
