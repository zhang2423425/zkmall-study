/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop;

import com.shop.zkmall.admin.page.finance.FinanceCount;
import com.shop.zkmall.admin.page.shop.Shop;
import com.shop.zkmall.admin.page.shop.ShopGetAll;
import com.shop.zkmall.admin.param.finance.FinanceParam;
import com.shop.zkmall.admin.param.shop.*;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformShopService {

    void save(ShopSaveParam param, CerePlatformUser user);

    CerePlatformShop findByShopName(String shopName);

    CerePlatformShop findByPhone(String shopPhone);

    CerePlatformShop checkShopIdByName(String shopName, Long shopId);

    CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId);

    void update(ShopUpdateParam param, CerePlatformUser user);

    void startOrStop(ShopStartParam param, CerePlatformUser user);

    ShopGetAll getById(Long shopId);

    Page<ShopGetAll> getAll(ShopGetAllParam param);

    Shop findShop(Long shopId);

    void updateDate(CerePlatformShop cerePlatformShop);

    FinanceCount getAllFinance(FinanceParam param);

    Long findBusinessId(Long shopId);

    List<CerePlatformShop> findAll();

    void updateShopStop(CerePlatformShop shop);

    Boolean cleanShop(Long shopId);

    void cloneShop(ShopCloneParam param, CerePlatformUser user);

    List<Shop> selectHotShopList();
}
