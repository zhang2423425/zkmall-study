/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.collect;

import com.shop.zkmall.app.page.collect.BuyerFootprint;
import com.shop.zkmall.app.param.collect.FootprintIdParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.collect.CereBuyerFootprint;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.common.PageParam;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerFootprintService {

    void insert(CereBuyerFootprint cereBuyerFootprint);

    void delete(FootprintIdParam param, CereBuyerUser user);

    Page<BuyerFootprint> getAll(PageParam param, Long buyerUserId);

    void selected(FootprintIdParam param, CereBuyerUser user);

    void updateBuyerData(Long buyerUserId, Long id);

    long findFootprintCount(Long buyerUserId);
}
