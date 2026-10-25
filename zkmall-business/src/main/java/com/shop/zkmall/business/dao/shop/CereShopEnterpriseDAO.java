/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.param.shop.CereShopEnterpriseParam;
import com.shop.zkmall.commons.domain.shop.CereShopEnterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopEnterpriseDAO extends BaseMapper<CereShopEnterprise> {

    int insertSelective(CereShopEnterprise record);

    void updateData(CereShopEnterprise enterprise);

    CereShopEnterprise findByShopId(@Param("shopId") Long shopId);

    void insertParam(CereShopEnterpriseParam param);

    void updateParam(CereShopEnterpriseParam param);
}
