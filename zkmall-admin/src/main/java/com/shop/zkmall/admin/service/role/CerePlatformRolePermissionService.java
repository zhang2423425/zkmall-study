/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.role;

import com.shop.zkmall.commons.domain.role.CerePlatformRolePermission;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformRolePermissionService {

    void deleteByRoleId(Long roleId);

    void insert(CerePlatformRolePermission cerePlatformRolePermission);

    void insertBatch(List<CerePlatformRolePermission> rolePermissions);

    void deleteByPermissionId(Long permissionId);
}
