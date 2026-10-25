/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.canvas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.param.canvas.CanvasPageQueryParam;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformCanvasPageDAO extends BaseMapper<CerePlatformCanvasPage> {

    int deleteByPrimaryKey(Long canvasId);

    int insertSelective(CerePlatformCanvasPage record);

    CerePlatformCanvasPage selectByPrimaryKey(Long canvasId);

    int updateByPrimaryKeySelective(CerePlatformCanvasPage record);

    CerePlatformCanvasPage getCanvas(CerePlatformCanvasPage canvas);

    List<CerePlatformCanvasPage> selectHomePageCanvasList(CanvasPageQueryParam param);

    int cleanHomePage(Integer terminal);

    int settingHomePage(Long canvasId);

    List<CerePlatformCanvasPage> getTemplatePage(CanvasPageQueryParam param);
}
