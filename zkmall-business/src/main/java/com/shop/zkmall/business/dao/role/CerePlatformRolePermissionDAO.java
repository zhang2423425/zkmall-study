/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.role.CerePlatformRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformRolePermissionDAO extends BaseMapper<CerePlatformRolePermission> {

    int insertSelective(CerePlatformRolePermission record);

    void deleteByRoleId(@Param("roleId") Long roleId);

    void insertBatch(@Param("list") List<CerePlatformRolePermission> list);

    Long check(@Param("roleId") Long roleId);

    void deleteByPermissionId(@Param("permissionId") Long permissionId);
}
