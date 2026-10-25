/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.param.shop.CereShopIndividualBusinessesParam;
import com.shop.zkmall.commons.domain.shop.CereShopIndividualBusinesses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopIndividualBusinessesDAO extends BaseMapper<CereShopIndividualBusinesses> {

    int insertSelective(CereShopIndividualBusinesses record);

    void updateIndividual(CereShopIndividualBusinesses individualBusinesses);

    CereShopIndividualBusinesses findByShopId(@Param("shopId") Long shopId);

    void insertParam(CereShopIndividualBusinessesParam param);

    void updateParam(CereShopIndividualBusinessesParam param);

}
