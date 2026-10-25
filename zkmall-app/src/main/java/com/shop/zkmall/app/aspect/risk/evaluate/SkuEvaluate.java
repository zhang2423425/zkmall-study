/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk.evaluate;

import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.app.aspect.risk.rule.Evaluate;
import com.shop.zkmall.app.aspect.risk.rule.RiskFactor;
import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.app.param.order.OrderProductParam;
import com.shop.zkmall.app.param.settlement.ProductSku;
import com.shop.zkmall.commons.domain.risk.CereRiskRule;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
public class SkuEvaluate extends Evaluate {

    public SkuEvaluate() {
        super(RiskFactor.WAIT_PAY_NUM);
    }

    @Override
    public boolean needCheck(CereRiskRule rule) {
        return rule.getRuleSwitchSku() == 1;
    }

    @Override
    public boolean eval(CereRiskRule rule, Long buyerUserId, Object[] args) {
        JSONObject jsonObject = JSONObject.parseObject(rule.getRuleSkuLimit());
        if (!jsonObject.containsKey("num") || !jsonObject.containsKey("compare")) {
            return true;
        }
        Long num = jsonObject.getObject("num", Long.class);
        String compare = jsonObject.getObject("compare", String.class);
        if (args != null && args.length > 0) {
            if (args[0] instanceof OrderParam) {
                OrderParam orderParam = (OrderParam) args[0];
                if (orderParam.getShops() != null) {
                    for (OrderProductParam param : orderParam.getShops()) {
                        for (ProductSku sku : param.getSkus()) {
                            //达到风控条件
                            boolean matchCondition = calc(compare, sku.getNumber().longValue(), num);
                            if (matchCondition) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;

//        return !calc(compare, total, num);
    }
}
