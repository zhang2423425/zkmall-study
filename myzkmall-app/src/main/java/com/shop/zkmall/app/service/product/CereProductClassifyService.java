/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product;

import com.shop.zkmall.app.page.classify.Classify;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.index.ProductClassify;
import com.shop.zkmall.app.param.classify.ClassifyParam;
import com.shop.zkmall.app.param.classify.ClassifyProductParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductClassifyService {

    List<ProductClassify> findAll();

    List<Classify> getFirstClassify(ClassifyParam param);

    Page<Product> getClassifyProducts(ClassifyProductParam param, CereBuyerUser user);

    Page<Product> getClassifyProducts2(ClassifyProductParam param, CereBuyerUser user);
}

