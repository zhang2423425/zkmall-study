/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk.handler;

import com.shop.zkmall.app.aspect.risk.AbstractRiskCheckHandler;
import com.shop.zkmall.app.aspect.risk.RiskEvaluateRegistry;
import com.shop.zkmall.app.service.risk.CereRiskBlackService;
import com.shop.zkmall.app.service.risk.CereRiskRuleService;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.risk.CereRiskBlack;
import com.shop.zkmall.commons.domain.risk.CereRiskRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
@RequiredArgsConstructor
public class DefaultRiskCheckHandler extends AbstractRiskCheckHandler {

    private final RiskEvaluateRegistry registry;

    private final CereRiskBlackService riskBlackService;

    private final CereRiskRuleService riskRuleService;

    @Override
    protected Long getBuyerUserId() {
        CereBuyerUser user = (CereBuyerUser) super.innerRequest.getAttribute("user");
        if (user != null) {
            return user.getBuyerUserId();
        }
        return null;
    }

    @Override
    protected Optional<CereRiskRule> getRiskRule() {
        return riskRuleService.getEnabled();
    }

    @Override
    protected List<CereRiskBlack> getRiskBlack() {
        return riskBlackService.getEnabled();
    }

    @Override
    protected RiskEvaluateRegistry getRiskEvaluateRegistry() {
        return registry;
    }
}
