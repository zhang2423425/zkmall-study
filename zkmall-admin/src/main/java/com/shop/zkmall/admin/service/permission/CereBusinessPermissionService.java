/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.permission;

import com.shop.zkmall.admin.page.permission.MenuButton;
import com.shop.zkmall.admin.page.permission.Permission;
import com.shop.zkmall.admin.page.permission.RolePermission;
import com.shop.zkmall.admin.param.permission.*;
import com.shop.zkmall.admin.param.role.RoleDistributionParam;
import com.shop.zkmall.admin.param.user.UserBuildParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBusinessPermissionService {

    void save(PermissionSaveParam param, CerePlatformUser user);

    void update(PermissionUpdateParam param, CerePlatformUser user);

    void delete(PermissionDeleteParam param, CerePlatformUser user);

    CerePlatformPermission getById(Long permissionId);

    Page<Permission> getAll(PermissionGetAllParam param);

    List<MenuButton> getAllByUserId(UserBuildParam user);

    CerePlatformPermission findBySort(Integer sort, Long id);

    Integer getMaxSort();

    RolePermission getRolePermission(RoleDistributionParam param);

    List<MenuButton> findAllPermissionShop();

    void insert(CerePlatformPermission permission);

    boolean syncMenu(SyncMenuParam param);

}
