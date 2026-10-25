/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.index;

import com.shop.zkmall.app.page.index.Index;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.param.index.IndexParam;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerSearch;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.canvas.CerePlatformCanvas;
import com.shop.zkmall.commons.domain.common.Page;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface IndexService {

    Index index(IndexParam param, CereBuyerUser user);

    List<CereBuyerSearch> getHistory(CereBuyerUser user);

    Page<Product> getSearchProducts(SearchParam param, CereBuyerUser user);

    void deleteSearch(SearchParam param);

    CerePlatformCanvas getCanvas(CerePlatformCanvas canvas);
}
