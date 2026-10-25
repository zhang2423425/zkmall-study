/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.product;

import com.shop.zkmall.admin.page.product.ProductClassify;
import com.shop.zkmall.admin.param.product.ClassifyDeleteParam;
import com.shop.zkmall.admin.param.product.ClassifyGetAllParam;
import com.shop.zkmall.admin.param.product.ClassifyLevelParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
import com.shop.zkmall.commons.domain.product.Classify;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductClassifyService {

    void save(ClassifyLevelParam param, CerePlatformUser user);

    void update(ClassifyLevelParam param, CerePlatformUser user);

    ProductClassify getById(Long classifyId);

    Page<CereProductClassify> getAll(ClassifyGetAllParam param);

    void delete(ClassifyDeleteParam param, CerePlatformUser user);

    List<Classify> getClassify();

    List<Classify> getClassifyByIds(List<Long> ids);
}
