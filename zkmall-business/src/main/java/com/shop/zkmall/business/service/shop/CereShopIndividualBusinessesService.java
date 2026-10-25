/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CereShopIndividualBusinesses;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopIndividualBusinessesService {

    void individual(CereShopIndividualBusinesses individualBusinesses, CerePlatformBusiness user);

    void updateIndividual(CereShopIndividualBusinesses individualBusinesses, CerePlatformBusiness user);

    CereShopIndividualBusinesses findByShopId(Long shopId);

}
