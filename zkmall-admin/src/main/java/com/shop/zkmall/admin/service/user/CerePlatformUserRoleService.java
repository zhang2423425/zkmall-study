/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.user;

import com.shop.zkmall.admin.page.role.PlatformUserRole;
import com.shop.zkmall.commons.domain.user.CerePlatformUserRole;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformUserRoleService {

    void insert(CerePlatformUserRole cerePlatformUserRole);

    void deleteByUserId(Long platformUserId);

    List<PlatformUserRole> findRolesByUserId(Long platformUserId);

    List<Long> findByRoleId(Long roleId);
}
