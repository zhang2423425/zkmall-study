/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.business;

import com.shop.zkmall.business.page.role.PlatformUserRole;
import com.shop.zkmall.commons.domain.business.CereBusinessUserRole;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBusinessUserRoleService {

    void insert(CereBusinessUserRole businessUserRole);

    void deleteByUserId(Long businessUserId);

    List<PlatformUserRole> findRolesByUserId(Long businessUserId);

    List<Long> findByRoleId(Long roleId);
}
