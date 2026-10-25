/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.business.param.finance.FinanceSaveWithdralwalParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopWithdrawalService {

    void save(FinanceSaveWithdralwalParam withdrawal, CerePlatformBusiness user);

}
