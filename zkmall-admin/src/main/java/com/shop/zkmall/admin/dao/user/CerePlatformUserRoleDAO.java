/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.role.PlatformUserRole;
import com.shop.zkmall.commons.domain.user.CerePlatformUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformUserRoleDAO extends BaseMapper<CerePlatformUserRole> {

    int insertSelective(CerePlatformUserRole record);

    void deleteByUserId(@Param("platformUserId") Long platformUserId);

    List<PlatformUserRole> findRolesByUserId(@Param("platformUserId") Long platformUserId);

    List<Long> findByRoleId(Long roleId);
}
