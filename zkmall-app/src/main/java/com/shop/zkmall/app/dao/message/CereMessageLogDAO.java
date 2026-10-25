/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.message.CereMessageLog;
import com.shop.zkmall.commons.domain.message.CerePlatfromMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereMessageLogDAO extends BaseMapper<CereMessageLog> {

    int insertSelective(CereMessageLog record);

    CerePlatfromMessage findMessage(@Param("template") String template);
}
