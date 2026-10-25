/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.business.page.shop.ShopBanner;
import com.shop.zkmall.business.param.banner.BannerSaveParam;
import com.shop.zkmall.business.param.banner.BannerUpdateParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopBannerService {

    void save(BannerSaveParam param, CerePlatformBusiness user);

    void update(BannerUpdateParam param, CerePlatformBusiness user);

    ShopBanner getById(Long bannerId);
}
