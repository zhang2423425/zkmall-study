/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.permission.MenuButton;
import com.shop.zkmall.business.page.permission.Permission;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformPermissionDAO extends BaseMapper<CerePlatformPermission> {

    int deleteByPrimaryKey(Long permissionId);

    int insertSelective(CerePlatformPermission record);

    CerePlatformPermission selectByPrimaryKey(Long permissionId);

    int updateByPrimaryKeySelective(CerePlatformPermission record);

    int updateByPrimaryKey(CerePlatformPermission record);

    CerePlatformPermission getById(@Param("permissionId") Long permissionId);

    List<Permission> findAllByResourceType(@Param("resourceType") String resourceType,
                                           @Param("permissionName") String permissionName, @Param("shopId") Long shopId);

    List<MenuButton> findAllCatalogByUserIdAndResourceType(@Param("businessUserId") Long businessUserId,
                                                           @Param("resourceType") String resourceType, @Param("shopId") Long shopId);

    CerePlatformPermission findBySort(@Param("sort") Integer sort, @Param("id") Long id, @Param("shopId") Long shopId);

    Integer getMaxSort();

    List<Long> findRolePermissionIds(@Param("roleId") Long roleId);

    List<Permission> findChilds(@Param("permissionName") String permissionName, @Param("shopId") Long shopId);

    int findMaxSort();
}
