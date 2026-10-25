/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.shop.ShopBanner;
import com.shop.zkmall.commons.domain.shop.CereShopBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopBannerDAO extends BaseMapper<CereShopBanner> {

    int deleteByPrimaryKey(Long bannerId);

    int insertSelective(CereShopBanner record);

    CereShopBanner selectByPrimaryKey(Long bannerId);

    int updateByPrimaryKeySelective(CereShopBanner record);

    int updateByPrimaryKey(CereShopBanner record);

    ShopBanner getById(@Param("bannerId") Long bannerId);
}
