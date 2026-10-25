/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.shop;

import com.shop.zkmall.admin.page.shop.Shop;
import com.shop.zkmall.admin.param.shopcheck.CheckDeleteParam;
import com.shop.zkmall.admin.param.shopcheck.CheckGetAllParam;
import com.shop.zkmall.admin.param.shopcheck.CheckHandleParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopCheckService {

    Page<Shop> getAll(CheckGetAllParam param);

    Shop getById(Long shopId);

    void handle(CheckHandleParam param, CerePlatformUser user) throws Exception;

    void delete(CheckDeleteParam param, CerePlatformUser user);

    void setInitPermission(Long shopId, String time);
}
