/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.role.impl;

import com.shop.zkmall.business.dao.role.CerePlatformRolePermissionDAO;
import com.shop.zkmall.business.service.role.CerePlatformRolePermissionService;
import com.shop.zkmall.commons.domain.role.CerePlatformRolePermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CerePlatformRolePermissionServiceImpl implements CerePlatformRolePermissionService {

    private final CerePlatformRolePermissionDAO cerePlatformRolePermissionDAO;

    @Override
    public void deleteByRoleId(Long roleId) {
        cerePlatformRolePermissionDAO.deleteByRoleId(roleId);
    }

    @Override
    public void insert(CerePlatformRolePermission cerePlatformRolePermission) {
        cerePlatformRolePermissionDAO.insert(cerePlatformRolePermission);
    }

    @Override
    public void insertBatch(List<CerePlatformRolePermission> list) {
        cerePlatformRolePermissionDAO.insertBatch(list);
    }

    @Override
    public Long check(Long roleId) {
        return cerePlatformRolePermissionDAO.check(roleId);
    }

    @Override
    public void deleteByPermissionId(Long permissionId) {
        cerePlatformRolePermissionDAO.deleteByPermissionId(permissionId);
    }
}
