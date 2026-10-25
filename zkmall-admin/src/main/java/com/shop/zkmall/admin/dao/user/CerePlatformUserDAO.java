/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.user.PlatformUser;
import com.shop.zkmall.admin.param.user.UserGetAllParam;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformUserDAO extends BaseMapper<CerePlatformUser> {

    int deleteByPrimaryKey(Long platformUserId);

    int insertSelective(CerePlatformUser record);

    CerePlatformUser selectByPrimaryKey(Long platformUserId);

    int updateByPrimaryKeySelective(CerePlatformUser record);

    int updateByPrimaryKey(CerePlatformUser record);

    CerePlatformUser findByUserName(@Param("userName") String userName);

    CerePlatformUser findByPhone(@Param("phone") String phone);

    PlatformUser getById(@Param("platformUserId") Long platformUserId);

    List<PlatformUser> getAll(UserGetAllParam userParam);

    CerePlatformUser findByToken(@Param("token") String token);

    void updateToken(CerePlatformUser cerePlatformUser);

    CerePlatformUser checkUserNameOrPhone(@Param("username") String username, @Param("phone") String phone, @Param("platformUserId") Long platformUserId);
}
