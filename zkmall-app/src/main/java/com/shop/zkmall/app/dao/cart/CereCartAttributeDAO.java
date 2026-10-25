/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.cart;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.cart.CereCartAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereCartAttributeDAO extends BaseMapper<CereCartAttribute> {

    int insertSelective(CereCartAttribute record);

    void insertBatch(@Param("list") List<CereCartAttribute> list);

    List<CereCartAttribute> selectCartSkuValueGroup(List<Long> cartIdList);
}
