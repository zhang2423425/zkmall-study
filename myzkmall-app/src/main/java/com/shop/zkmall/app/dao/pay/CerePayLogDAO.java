/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.pay.CerePayLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePayLogDAO extends BaseMapper<CerePayLog> {

    int deleteByPrimaryKey(Long id);

    int insertSelective(CerePayLog record);

    CerePayLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CerePayLog record);

    int updateByPrimaryKey(CerePayLog record);
}
