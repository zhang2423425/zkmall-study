/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.logistics;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.logistics.Charge;
import com.shop.zkmall.commons.domain.logistics.CereLogisticsCharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereLogisticsChargeDAO extends BaseMapper<CereLogisticsCharge> {

    int insertSelective(CereLogisticsCharge record);

    void insertBatch(@Param("list") List<CereLogisticsCharge> list);

    void deleteByLogisticsId(@Param("logisticsId") Long logisticsId);

    List<Charge> findByLogisticsId(@Param("logisticsId") Long logisticsId);
}
