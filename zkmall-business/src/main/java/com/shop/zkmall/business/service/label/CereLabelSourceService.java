/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.label;

import com.shop.zkmall.business.page.shop.LabelSource;
import com.shop.zkmall.business.param.label.LabelDeleteSourceParam;
import com.shop.zkmall.business.param.label.LabelGetSourceParam;
import com.shop.zkmall.business.param.label.LabelSaveSourceParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.label.CereLabelSource;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereLabelSourceService {

    List<CereLabelSource> findByLabelId(Long labelId);

    void deleteByLabelId(Long labelId);

    void insertBatch(List<CereLabelSource> list);

    void saveSource(LabelSaveSourceParam param, CerePlatformBusiness user);

    List<LabelSource> getAllByLabel(LabelGetSourceParam param);

    void deleteSource(LabelDeleteSourceParam param, CerePlatformBusiness user);

    List<CereLabelSource> findByLabelIdAndType(Long labelId, Integer labelType);

    void updateSource(LabelSaveSourceParam param, CerePlatformBusiness user);
}
