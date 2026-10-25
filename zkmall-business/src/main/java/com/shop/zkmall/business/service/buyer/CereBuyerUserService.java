/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.buyer;

import com.shop.zkmall.business.page.buyer.BuyerUser;
import com.shop.zkmall.business.page.buyer.BuyerUserDetail;
import com.shop.zkmall.business.param.buyer.BuyerUserGetAllParam;
import com.shop.zkmall.business.param.buyer.UserSaveParam;
import com.shop.zkmall.business.param.buyer.UserUpdateParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerUserService {

    Page<BuyerUser> getAll(BuyerUserGetAllParam param);

    void save(UserSaveParam param, CerePlatformBusiness user);

    void update(UserUpdateParam param, CerePlatformBusiness user);

    BuyerUserDetail getById(Long shopId, Long buyerUserId);

    void initBusinessBuyerUser();

    void updateGrowth(Long buyerUserId, int growth);
}
