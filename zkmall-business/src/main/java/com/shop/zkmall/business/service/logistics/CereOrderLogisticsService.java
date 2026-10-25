/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.logistics;

import com.shop.zkmall.business.page.logistics.Logistics;
import com.shop.zkmall.business.param.logistics.LogistDeleteParam;
import com.shop.zkmall.business.param.logistics.LogistSaveParam;
import com.shop.zkmall.business.param.logistics.LogistUpdateParam;
import com.shop.zkmall.business.param.shop.ShopParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderLogisticsService {

    void save(LogistSaveParam param, CerePlatformBusiness user);

    void update(LogistUpdateParam param, CerePlatformBusiness user);

    void delete(LogistDeleteParam param, CerePlatformBusiness user);

    Logistics getById(Long logisticsId);

    Page<Logistics> getAll(ShopParam param);
}
