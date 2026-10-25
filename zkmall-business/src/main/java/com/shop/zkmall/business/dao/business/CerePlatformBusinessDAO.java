/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.shop.PlatformBusiness;
import com.shop.zkmall.business.page.user.Business;
import com.shop.zkmall.business.param.user.BusinessGetAllUser;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformBusinessDAO extends BaseMapper<CerePlatformBusiness> {

    int deleteByPrimaryKey(Long businessUserId);

    CerePlatformBusiness selectByPrimaryKey(Long businessUserId);

    int updateByPrimaryKeySelective(CerePlatformBusiness record);

    PlatformBusiness findByUserName(@Param("username") String username);

    PlatformBusiness findByPhone(@Param("phone") String phone);

    CerePlatformBusiness findByToken(@Param("token") String token);

    void updateToken(CerePlatformBusiness cerePlatformBusiness);

    List<CerePlatformBusiness> getAll(BusinessGetAllUser param);

    CerePlatformBusiness checkUserName(@Param("username") String username, @Param("businessUserId") Long businessUserId);

    Business getById(@Param("businessUserId") Long businessUserId);

    PlatformBusiness findById(@Param("businessUserId") Long userId);

    CerePlatformBusiness findAdminUser(Long shopId);

    CerePlatformBusiness checkPhone(@Param("phone") String phone, @Param("businessUserId") Long businessUserId);
}
