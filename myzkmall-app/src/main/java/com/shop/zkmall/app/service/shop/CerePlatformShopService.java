/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.shop;

import com.shop.zkmall.app.page.banner.ShopBanner;
import com.shop.zkmall.app.page.index.RecommendShop;
import com.shop.zkmall.app.page.settlement.SettlementShop;
import com.shop.zkmall.app.page.shop.PlatformShop;
import com.shop.zkmall.app.page.shop.Shop;
import com.shop.zkmall.app.page.shop.ShopClassify;
import com.shop.zkmall.app.page.shop.ShopIndex;
import com.shop.zkmall.app.param.shop.ShopParam;
import com.shop.zkmall.app.param.shop.ShopPosterParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;

import java.io.InputStream;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformShopService {

    List<RecommendShop> findRecommendShop();

    RecommendShop findVolumeProductByShopId(Long shopId);

    List<RecommendShop> findRecommendProducts();

    Integer findPayUsers(Long productId);

    Shop getShopProducts(ShopParam param, CereBuyerUser user);

    List<ShopClassify> getShopClassify(ShopParam param);

    List<ShopBanner> getShopBanner(Long shopId);

    Page<SettlementShop> getShops(ShopParam param, CereBuyerUser user);

    void updateState(CerePlatformShop cerePlatformShop);

    void insert(CerePlatformShop cerePlatformShop);

    void update(CerePlatformShop cerePlatformShop);

    PlatformShop getById(Long shopId);

    CerePlatformShop check(String shopPhone);

    ShopIndex getIndex(ShopParam param, CereBuyerUser user) throws Exception;

    InputStream getSharePic(ShopPosterParam param, CereBuyerUser user) throws Exception;

    List<ShopIndex> selectHotShopList();
}
