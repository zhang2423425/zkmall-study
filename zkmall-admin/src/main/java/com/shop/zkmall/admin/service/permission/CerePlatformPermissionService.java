/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.permission;

import com.shop.zkmall.admin.page.permission.MenuButton;
import com.shop.zkmall.admin.page.permission.Permission;
import com.shop.zkmall.admin.page.permission.RolePermission;
import com.shop.zkmall.admin.param.permission.PermissionDeleteParam;
import com.shop.zkmall.admin.param.permission.PermissionGetAllParam;
import com.shop.zkmall.admin.param.permission.PermissionSaveParam;
import com.shop.zkmall.admin.param.permission.PermissionUpdateParam;
import com.shop.zkmall.admin.param.role.RoleDistributionParam;
import com.shop.zkmall.admin.param.user.UserBuildParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformPermissionService {
    /**
     * 保存权限
     */
    void save(PermissionSaveParam param, CerePlatformUser user);

    /**
     * 更新权限
     */
    void update(PermissionUpdateParam param, CerePlatformUser user);

    /**
     * 删除权限
     */
    void delete(PermissionDeleteParam param, CerePlatformUser user);

    /**
     * 根据主键查询权限
     */
    CerePlatformPermission getById(Long permissionId);

    /**
     * 查询权限列表
     */
    Page<Permission> getAll(PermissionGetAllParam param);

    /**
     * 查询用户所有权限
     */
    List<MenuButton> getAllByUserId(UserBuildParam user);

    /**
     * 根据主键和排序查询单个权限
     */
    CerePlatformPermission findBySort(Integer sort, Long id);

    /**
     * 获取最大权限
     */
    Integer getMaxSort();

    /**
     * 获取角色权限
     */
    RolePermission getRolePermission(RoleDistributionParam param);

    /**
     * 查询所有权限
     */
    List<MenuButton> findAllPermissionShop();

    /**
     * 新增权限
     */
    void insert(CerePlatformPermission permission);

    /**
     * 获取权限最大主键
     */
    Long getMaxId();

    /**
     * 批量新增权限
     */
    void insertBatch(List<CerePlatformPermission> permissions);
}
