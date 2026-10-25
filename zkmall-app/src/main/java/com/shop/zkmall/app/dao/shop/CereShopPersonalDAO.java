/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.param.shop.CereShopPersonalParam;
import com.shop.zkmall.commons.domain.shop.CereShopPersonal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopPersonalDAO extends BaseMapper<CereShopPersonal> {

    int insertSelective(CereShopPersonal record);

    void updatePersonal(CereShopPersonal personal);

    CereShopPersonal findByShopId(@Param("shopId") Long shopId);

    void insertParam(CereShopPersonalParam param);
}
