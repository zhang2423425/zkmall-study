/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.canvas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformCanvasDAO extends BaseMapper<CerePlatformCanvas> {

    int deleteByPrimaryKey(Long canvasId);

    int insertSelective(CerePlatformCanvas record);

    CerePlatformCanvas selectByPrimaryKey(Long canvasId);

    int updateByPrimaryKeySelective(CerePlatformCanvas record);

    // int updateByPrimaryKey(CerePlatformCanvas record);

    CerePlatformCanvas getCanvas(CerePlatformCanvas canvas);

    CerePlatformCanvas checkCanvas(CerePlatformCanvas canvas);
}
