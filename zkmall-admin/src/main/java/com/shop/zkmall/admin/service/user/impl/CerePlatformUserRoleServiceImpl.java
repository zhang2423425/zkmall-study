/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.user.impl;

import com.shop.zkmall.admin.dao.user.CerePlatformUserRoleDAO;
import com.shop.zkmall.admin.page.role.PlatformUserRole;
import com.shop.zkmall.admin.service.user.CerePlatformUserRoleService;
import com.shop.zkmall.commons.domain.user.CerePlatformUserRole;
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
public class CerePlatformUserRoleServiceImpl implements CerePlatformUserRoleService {

    private final CerePlatformUserRoleDAO cerePlatformUserRoleDAO;

    @Override
    public void insert(CerePlatformUserRole cerePlatformUserRole) {
        cerePlatformUserRoleDAO.insert(cerePlatformUserRole);
    }

    @Override
    public void deleteByUserId(Long platformUserId) {
        cerePlatformUserRoleDAO.deleteByUserId(platformUserId);
    }

    @Override
    public List<PlatformUserRole> findRolesByUserId(Long platformUserId) {
        return cerePlatformUserRoleDAO.findRolesByUserId(platformUserId);
    }

    @Override
    public List<Long> findByRoleId(Long roleId) {
        return cerePlatformUserRoleDAO.findByRoleId(roleId);
    }
}
