/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.label;

import com.shop.zkmall.admin.param.label.*;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.label.CereBuyerLabel;
import com.shop.zkmall.commons.domain.label.CerePlatformLabel;
import com.shop.zkmall.commons.domain.label.PlatformLabel;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CerePlatformLabelService {

    Page<PlatformLabel> getAll(LabelGetAllParam param);

    PlatformLabel getById(Long buyerLabelId);

    void save(LabelSaveParam label, CerePlatformUser user);

    void update(LabelUpdateParam label, CerePlatformUser user);

    void delete(LabelDeleteParam param, CerePlatformUser user);

    List<PlatformLabel> findExcel(LabelExcelParam param);

    List<CerePlatformLabel> findAll();

    List<Long> findAllByDay(Integer consumptionDay);

    List<Long> findRangeDayBuyers(CerePlatformLabel label);

    List<Long> findFrequencyBuyes(CerePlatformLabel label);

    List<Long> findMoneyBuyers(CerePlatformLabel label);

    void insertBatchBuyerLabel(List<CereBuyerLabel> collect);

    List<Long> findAllBuyers(CerePlatformLabel label);
}
