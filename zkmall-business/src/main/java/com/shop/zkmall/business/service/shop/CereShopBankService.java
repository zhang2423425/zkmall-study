/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.business.page.shop.ShopBankDetail;
import com.shop.zkmall.business.param.bank.BankDeleteParam;
import com.shop.zkmall.business.param.bank.BankSaveParam;
import com.shop.zkmall.business.param.bank.BankUpdateParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import org.apache.ibatis.annotations.Param;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopBankService {

    void save(BankSaveParam param, CerePlatformBusiness user);

    void update(BankUpdateParam param, CerePlatformBusiness user);

    void delete(BankDeleteParam param, CerePlatformBusiness user);

    ShopBankDetail getById(@Param("shopId") Long shopId);

    CerePlatformShop findByPhone(Long shopId, String phone);
}
