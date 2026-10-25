/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.risk;

import com.shop.zkmall.admin.page.risk.CereRiskUserBlack;
import com.shop.zkmall.admin.param.risk.CereRiskBlackPageParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.risk.CereRiskBlack;

/**
 * <p>
 * 业务接口
 * 黑名单表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
public interface CereRiskBlackService {

    Page<CereRiskBlack> getAllIpBlackList(CereRiskBlackPageParam param);

    Page<CereRiskUserBlack> getUserBlackList(CereRiskBlackPageParam param);

    long save(CereRiskBlack param);

    int update(CereRiskBlack param);

    int delete(CereRiskBlack param);
}
