/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.label;

import com.shop.zkmall.business.page.label.ShopLabel;
import com.shop.zkmall.business.param.label.LabelDeleteParam;
import com.shop.zkmall.business.param.label.LabelGetAllParam;
import com.shop.zkmall.business.param.label.LabelSaveParam;
import com.shop.zkmall.business.param.label.LabelUpdateParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.label.CereShopLabel;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopLabelService {

    void save(LabelSaveParam param, CerePlatformBusiness user);

    void update(LabelUpdateParam label, CerePlatformBusiness user);

    void delete(LabelDeleteParam label, CerePlatformBusiness user);

    CereShopLabel getById(Long labelId);

    List<ShopLabel> getAll(LabelGetAllParam param);

    CereShopLabel findByShopIdNotGroup(Long shopId);

    void insert(CereShopLabel cereShopLabel);
}
