/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.business.impl;

import com.shop.zkmall.admin.dao.business.CereBusinessUserRoleDAO;
import com.shop.zkmall.admin.service.business.CereBusinessUserRoleService;
import com.shop.zkmall.commons.domain.business.CereBusinessUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereBusinessUserRoleServiceImpl implements CereBusinessUserRoleService {

    private final CereBusinessUserRoleDAO cereBusinessUserRoleDAO;

    @Override
    public void insert(CereBusinessUserRole cereBusinessUserRole) {
        cereBusinessUserRoleDAO.insert(cereBusinessUserRole);
    }
}
