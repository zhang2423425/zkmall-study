/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop;

import com.shop.zkmall.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.zkmall.admin.param.withdrawal.WithdrawalHandleParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopWithdrawal;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopWithdrawalService {

    Page<CereShopWithdrawal> getAll(WithdrawalGetAllParam param);

    CereShopWithdrawal getById(Long withdrawalId);

    void handle(WithdrawalHandleParam param, CerePlatformUser user);
}
