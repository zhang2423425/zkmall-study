/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.label;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.label.ShopLabel;
import com.shop.zkmall.business.param.label.LabelGetAllParam;
import com.shop.zkmall.commons.domain.label.CereShopLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopLabelDAO extends BaseMapper<CereShopLabel> {

    int deleteByPrimaryKey(Long labelId);

    int insertSelective(CereShopLabel record);

    CereShopLabel selectByPrimaryKey(Long labelId);

    int updateByPrimaryKeySelective(CereShopLabel record);

    int updateByPrimaryKey(CereShopLabel record);

    CereShopLabel findByShopIdNotGroup(@Param("shopId") Long shopId);

    CereShopLabel getById(@Param("labelId") Long labelId);

    List<ShopLabel> getAll(LabelGetAllParam param);
}
