/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.canvas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.param.canvas.CanvasPageParam;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvasCustom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformCanvasCustomDAO extends BaseMapper<CerePlatformCanvasCustom> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(CerePlatformCanvasCustom record);

    CerePlatformCanvasCustom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CerePlatformCanvasCustom record);

    List<CerePlatformCanvasCustom> selectCanvasCustomList(CanvasPageParam param);
}
