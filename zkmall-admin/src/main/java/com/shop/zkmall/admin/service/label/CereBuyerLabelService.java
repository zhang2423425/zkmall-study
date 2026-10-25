/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.label;

import com.shop.zkmall.admin.param.buyer.BuyerSaveUserLabelParam;
import com.shop.zkmall.commons.domain.label.CereBuyerLabel;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerLabelService {

    void insert(CereBuyerLabel cereBuyerLabel);

    void deleteLabelUser(List<Long> ids);

    void insertBatch(List<CereBuyerLabel> collect);

    List<Long> findAlreadyByUser(BuyerSaveUserLabelParam param);

    List<Long> findByBuyerUserId(Long buyerUserId);
}
