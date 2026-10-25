/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.risk;


import com.shop.zkmall.commons.domain.risk.CereRiskRule;

import java.util.Optional;

/**
 * <p>
 * 业务接口
 * 风控规则表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
public interface CereRiskRuleService {

    Optional<CereRiskRule> getEnabled();
}
