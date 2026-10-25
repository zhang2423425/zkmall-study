/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.log.CerePlatformLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformLogDAO extends BaseMapper<CerePlatformLog> {

    int deleteByPrimaryKey(Long logId);

    int insertSelective(CerePlatformLog record);

    CerePlatformLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(CerePlatformLog record);

    int updateByPrimaryKey(CerePlatformLog record);
}
