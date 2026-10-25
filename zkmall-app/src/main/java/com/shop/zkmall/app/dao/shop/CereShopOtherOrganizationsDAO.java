/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.param.shop.CereShopOtherOrganizationsParam;
import com.shop.zkmall.commons.domain.shop.CereShopOtherOrganizations;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopOtherOrganizationsDAO extends BaseMapper<CereShopOtherOrganizations> {

    int insertSelective(CereShopOtherOrganizations record);

    void updateOrganizations(CereShopOtherOrganizations otherOrganizations);

    CereShopOtherOrganizations findByShopId(@Param("shopId") Long shopId);

    void insertParam(CereShopOtherOrganizationsParam param);

    void updateParam(CereShopOtherOrganizationsParam param);
}
