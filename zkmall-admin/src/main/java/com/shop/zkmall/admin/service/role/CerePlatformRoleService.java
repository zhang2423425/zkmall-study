/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.role;

import com.shop.zkmall.admin.param.role.*;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.role.CerePlatformRole;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformRoleService {

    void save(RoleSaveParam param, CerePlatformUser user);

    void update(RoleUpdateParam param, CerePlatformUser user);

    void delete(RoleDeleteParam param, CerePlatformUser user);

    CerePlatformRole getById(Long roleId);

    Page<CerePlatformRole> getAll(RoleGetAllParam param);

    void distribution(RoleDistributionParam param, CerePlatformUser user);

    void insert(CerePlatformRole role);
}
