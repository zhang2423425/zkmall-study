/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.business.page.index.HotProductDTO;
import com.shop.zkmall.business.page.index.Index;
import com.shop.zkmall.business.page.index.OrderConvertDTO;
import com.shop.zkmall.business.page.index.UserVisitDTO;
import com.shop.zkmall.business.page.shop.Shop;
import com.shop.zkmall.business.param.index.IndexParam;
import com.shop.zkmall.business.param.shop.ShopUpdateLogoParam;
import com.shop.zkmall.commons.domain.business.CereBusinessShop;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformShopService {

    CerePlatformShop selectByPrimaryKey(Long shopId);

    CerePlatformShop findByShopName(String shopName);

    Shop getById(Long shopId);

    void update(ShopUpdateLogoParam param, CerePlatformBusiness user);

    void updateState(CerePlatformShop cerePlatformShop);

    Index index(IndexParam param);

    CerePlatformShop check(String shopPhone);

    void insert(CerePlatformShop cerePlatformShop);

    CerePlatformShop findByPhone(String shopPhone);

    void update(CerePlatformShop cerePlatformShop);

    CerePlatformShop checkShopIdByName(String shopName, Long shopId);

    CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId);

    Index indexTest(IndexParam param);

    Integer findPayUsers(Long productId);

    List<CereBusinessShop> findIds();

    Long findShopRoleId(Long id);

    List<CerePlatformShop> selectAll();

    List<UserVisitDTO> selectUserVisitForExport(IndexParam param);

    List<OrderConvertDTO> selectOrderConvertForExport(IndexParam param);

    List<HotProductDTO> selectHotProductForExport(IndexParam param);
}
