/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk.evaluate;

import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.app.aspect.risk.rule.Evaluate;
import com.shop.zkmall.app.aspect.risk.rule.RiskFactor;
import com.shop.zkmall.app.service.after.CereOrderAfterService;
import com.shop.zkmall.commons.domain.risk.CereRiskRule;
import org.springframework.stereotype.Component;


/**
 * 按照售后进行风控
 * @author zkmall zkmallvip@163.com
 */
@Component
public class PostSalesEvaluate extends Evaluate {

    private final CereOrderAfterService cereOrderAfterService;

    public PostSalesEvaluate(CereOrderAfterService cereOrderAfterService) {
        super(RiskFactor.POST_NUM);
        this.cereOrderAfterService = cereOrderAfterService;
    }

    @Override
    public boolean needCheck(CereRiskRule rule) {
        return rule.getRuleSwitchPostSale() == 1;
    }

    @Override
    public boolean eval(CereRiskRule rule, Long buyerUserId, Object[] args) {
        JSONObject jsonObject = JSONObject.parseObject(rule.getRulePostSaleLimit());
        if (!jsonObject.containsKey("timeNum") || !jsonObject.containsKey("num") || !jsonObject.containsKey("compare")) {
            return true;
        }
        Integer timeNum = jsonObject.getObject("timeNum", Integer.class);
        Long num = jsonObject.getObject("num", Long.class);
        String compare = jsonObject.getObject("compare", String.class);

        Long total = cereOrderAfterService.getPostSaleNumByMonth(timeNum, buyerUserId);

        return !calc(compare, total, num);
    }
}
