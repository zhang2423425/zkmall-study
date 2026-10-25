/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.logistics;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.logistics.Logistics;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereOrderLogisticsDAO extends BaseMapper<CereOrderLogistics> {

    int deleteByPrimaryKey(Long logisticsId);

    int insertSelective(CereOrderLogistics record);

    CereOrderLogistics selectByPrimaryKey(Long logisticsId);

    int updateByPrimaryKeySelective(CereOrderLogistics record);

    int updateByPrimaryKey(CereOrderLogistics record);

    Logistics getById(@Param("logisticsId") Long logisticsId);

    List<Logistics> getAll(ShopParam param);
}
