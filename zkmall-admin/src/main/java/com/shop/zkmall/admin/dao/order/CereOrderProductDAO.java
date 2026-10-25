/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.domain.order.CereOrderProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereOrderProductDAO extends BaseMapper<CereOrderProduct> {

    int insertSelective(CereOrderProduct record);

    void insertBatchAttibutes(@Param("list") List<CereOrderProductAttribute> list);
}
