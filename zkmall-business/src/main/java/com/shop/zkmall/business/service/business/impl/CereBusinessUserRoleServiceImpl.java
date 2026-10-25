/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.business.impl;

import com.shop.zkmall.business.dao.business.CereBusinessUserRoleDAO;
import com.shop.zkmall.business.page.role.PlatformUserRole;
import com.shop.zkmall.business.service.business.CereBusinessUserRoleService;
import com.shop.zkmall.commons.domain.business.CereBusinessUserRole;
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
public class CereBusinessUserRoleServiceImpl implements CereBusinessUserRoleService {

    private final CereBusinessUserRoleDAO cereBusinessUserRoleDAO;

    @Override
    public void insert(CereBusinessUserRole businessUserRole) {
        cereBusinessUserRoleDAO.insert(businessUserRole);
    }

    @Override
    public void deleteByUserId(Long businessUserId) {
        cereBusinessUserRoleDAO.deleteByUserId(businessUserId);
    }

    @Override
    public List<PlatformUserRole> findRolesByUserId(Long businessUserId) {
        return cereBusinessUserRoleDAO.findRolesByUserId(businessUserId);
    }

    @Override
    public List<Long> findByRoleId(Long roleId) {
        return cereBusinessUserRoleDAO.findByRoleId(roleId);
    }
}
