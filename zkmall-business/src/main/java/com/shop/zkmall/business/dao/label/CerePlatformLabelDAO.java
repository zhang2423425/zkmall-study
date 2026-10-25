/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.label;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.label.CerePlatformLabel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformLabelDAO extends BaseMapper<CerePlatformLabel> {

    int deleteByPrimaryKey(Long buyerLabelId);

    int insertSelective(CerePlatformLabel record);

    CerePlatformLabel selectByPrimaryKey(Long buyerLabelId);

    int updateByPrimaryKeySelective(CerePlatformLabel record);

    int updateByPrimaryKey(CerePlatformLabel record);
}
