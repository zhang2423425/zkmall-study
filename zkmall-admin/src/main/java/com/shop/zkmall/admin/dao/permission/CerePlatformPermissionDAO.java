/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.permission.MenuButton;
import com.shop.zkmall.admin.page.permission.Permission;
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

    List<Permission> findAllByResourceType(@Param("resourceType") String resourceType, @Param("permissionName") String permissionName);

    List<MenuButton> findAllCatalogByUserIdAndResourceType(@Param("platformUserId") Long platformUserId, @Param("resourceType") String resourceType);

    CerePlatformPermission findBySort(@Param("sort") Integer sort, @Param("id") Long id);

    Integer getMaxSort();

    List<Long> findRolePermissionIds(@Param("roleId") Long roleId);

    List<Permission> findChilds(@Param("permissionName") String permissionName);

    List<MenuButton> findAllCatalogByResourceType(@Param("resourceType") String resourceType);

    // int findMaxSort();

    List<CerePlatformPermission> findByPermissionAndProject(@Param("project") Long project,
                                                            @Param("permission") String permission,
                                                            @Param("resourceType") String resourceType);

    Long getMaxId();

    void insertBatch(@Param("list") List<CerePlatformPermission> list);
}
