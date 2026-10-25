/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.logistics;

import com.shop.zkmall.commons.domain.logistics.CereLogisticsCharge;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderLogisticsService {

    List<CereOrderLogistics> findLogistics(Long shopId);

    List<CereLogisticsCharge> findCharges(Long logisticsId);
}
