/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.shop.ShopBankDetail;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.shop.CereShopBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopBankDAO extends BaseMapper<CereShopBank> {

    int insertSelective(CereShopBank record);

    void updateData(CereShopBank bank);

    void deleteData(CereShopBank bank);

    ShopBankDetail getById(@Param("shopId") Long shopId);

    CerePlatformShop findByPhone(@Param("shopId") Long shopId, @Param("phone") String phone);
}
