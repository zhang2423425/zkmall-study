/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk.evaluate;

import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.app.aspect.risk.rule.Evaluate;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.commons.domain.risk.CereRiskRule;
import org.springframework.stereotype.Component;

/**
 * 按照待支付进行风控
 * @author zkmall zkmallvip@163.com
 */
@Component
public class WaitPayEvaluate extends Evaluate {

    private final CereShopOrderService cereShopOrderService;

    public WaitPayEvaluate(CereShopOrderService cereShopOrderService) {
        super(com.shop.zkmall.app.aspect.risk.rule.RiskFactor.WAIT_PAY_NUM);
        this.cereShopOrderService = cereShopOrderService;
    }

    @Override
    public boolean needCheck(CereRiskRule rule) {
        return rule.getRuleSwitchWaitPay() == 1;
    }

    @Override
    public boolean eval(CereRiskRule rule, Long buyerUserId, Object[] args) {
        JSONObject jsonObject = JSONObject.parseObject(rule.getRuleWaitPayLimit());
        if (!jsonObject.containsKey("timeNum") || !jsonObject.containsKey("num") || !jsonObject.containsKey("compare")) {
            return true;
        }
        Integer timeNum = jsonObject.getObject("timeNum", Integer.class);
        Long num = jsonObject.getObject("num", Long.class);
        String compare = jsonObject.getObject("compare", String.class);

        Long total = cereShopOrderService.getWaitPayNumByHours(timeNum, buyerUserId);

        return !calc(compare, total, num);
    }
}
