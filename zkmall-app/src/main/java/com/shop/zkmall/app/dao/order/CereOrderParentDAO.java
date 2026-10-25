/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.order.CereOrderParent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereOrderParentDAO extends BaseMapper<CereOrderParent> {

    int deleteByPrimaryKey(Long parentId);

    int insertSelective(CereOrderParent record);

    CereOrderParent selectByPrimaryKey(Long parentId);

    int updateByPrimaryKeySelective(CereOrderParent record);

    int updateByPrimaryKey(CereOrderParent record);

    CereOrderParent findById(@Param("id") Long id);

}
