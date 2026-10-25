/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.collect;

import com.shop.zkmall.app.page.collect.CollectProduct;
import com.shop.zkmall.app.page.collect.CollectShop;
import com.shop.zkmall.app.param.collect.CollectGetAllParam;
import com.shop.zkmall.app.param.collect.CollectIdParam;
import com.shop.zkmall.app.param.collect.CollectParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.collect.CereBuyerCollect;
import com.shop.zkmall.commons.domain.common.Page;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerCollectService {

    void collect(CollectParam param, CereBuyerUser user);

    CereBuyerCollect findByUserProduct(Long buyerUserId, Long productId);

    CereBuyerCollect findByUserShopId(Long buyerUserId, Long shopId);

    void cancel(CollectIdParam param, CereBuyerUser user);

    void newCancel(CollectParam param, CereBuyerUser user);

    Page<CollectProduct> getAllProduct(CollectGetAllParam param, CereBuyerUser user);

    Page<CollectShop> getAllShop(CollectGetAllParam param, CereBuyerUser user);

    void selected(CollectIdParam param, CereBuyerUser user);

    void delete(CollectIdParam param, CereBuyerUser user);

    void updateBuyerData(Long buyerUserId, Long id);

    int findShopCollectCount(Long shopId);

    long findAllCollectCount(Long buyerUserId);
}
