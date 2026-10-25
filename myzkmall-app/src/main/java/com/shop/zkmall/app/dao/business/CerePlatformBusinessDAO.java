/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformBusinessDAO extends BaseMapper<CerePlatformBusiness> {

    int deleteByPrimaryKey(Long businessUserId);

    int insertSelective(CerePlatformBusiness record);

    CerePlatformBusiness selectByPrimaryKey(Long businessUserId);

    int updateByPrimaryKeySelective(CerePlatformBusiness record);

    int updateByPrimaryKey(CerePlatformBusiness record);

    CerePlatformBusiness checkUserName(@Param("shopPhone") String shopPhone);

    CerePlatformBusiness checkPhone(String phone);
}
