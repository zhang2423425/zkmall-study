/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.buyer;

import com.shop.zkmall.admin.page.buyer.BuyerWithdrawal;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.zkmall.admin.param.withdrawal.BuyerWithdrawalGetByIdParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerWithdrawalService {

    Page<BuyerWithdrawal> getAll(BuyerWithdrawalGetAllParam param);

    BuyerWithdrawal getById(Long withdrawalId);

    void handle(BuyerWithdrawalGetByIdParam param, CerePlatformUser user);
}
