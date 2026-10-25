/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CereShopEnterprise;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopEnterpriseService {

    void enterprise(CereShopEnterprise enterprise, CerePlatformBusiness user);

    void updateEnterprise(CereShopEnterprise enterprise, CerePlatformBusiness user);

    CereShopEnterprise findByShopId(Long shopId);

}
