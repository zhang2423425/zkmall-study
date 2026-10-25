/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.logistics;

import com.shop.zkmall.business.page.logistics.Charge;
import com.shop.zkmall.commons.domain.logistics.CereLogisticsCharge;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereLogisticsChargeService {

    void insertBatch(List<CereLogisticsCharge> charges);

    void deleteByLogisticsId(Long logisticsId);

    List<Charge> findByLogisticsId(Long logisticsId);
}
