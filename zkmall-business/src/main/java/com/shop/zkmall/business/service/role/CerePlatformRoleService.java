/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.role;

import com.shop.zkmall.business.param.permission.RoleDistributionParam;
import com.shop.zkmall.business.param.role.RoleDeleteParam;
import com.shop.zkmall.business.param.role.RoleGetAllParam;
import com.shop.zkmall.business.param.role.RoleSaveParam;
import com.shop.zkmall.business.param.role.RoleUpdateParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.role.CerePlatformRole;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformRoleService {

    void save(RoleSaveParam param, CerePlatformBusiness user);

    void update(RoleUpdateParam param, CerePlatformBusiness user);

    void delete(RoleDeleteParam param, CerePlatformBusiness user);

    CerePlatformRole getById(Long roleId);

    Page<CerePlatformRole> getAll(RoleGetAllParam param);

    void distribution(RoleDistributionParam param, CerePlatformBusiness user);
}
