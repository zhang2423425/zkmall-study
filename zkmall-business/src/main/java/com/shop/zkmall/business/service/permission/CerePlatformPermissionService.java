/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.permission;

import com.shop.zkmall.business.page.permission.MenuButton;
import com.shop.zkmall.business.page.permission.Permission;
import com.shop.zkmall.business.page.permission.RolePermission;
import com.shop.zkmall.business.param.permission.*;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformPermissionService {

    void save(PermissionSaveParam param, CerePlatformBusiness user);

    void update(PermissionUpdateParam param, CerePlatformBusiness user);

    void delete(PermissionDeleteParam param, CerePlatformBusiness user);

    CerePlatformPermission getById(Long permissionId);

    Page<Permission> getAll(PermissionGetAllParam param);

    List<MenuButton> getAllByUserId(UserBuildParam user);

    CerePlatformPermission findBySort(Integer sort, Long id, Long shopId);

    Integer getMaxSort();

    RolePermission getRolePermission(RoleDistributionParam param);

    int findMaxSort();

    void insert(CerePlatformPermission permission);
}
