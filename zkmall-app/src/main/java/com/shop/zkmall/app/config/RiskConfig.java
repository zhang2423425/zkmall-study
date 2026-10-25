/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.config;

import com.shop.zkmall.app.aspect.RiskCheckAspect;
import com.shop.zkmall.app.aspect.risk.RiskCheckHandler;
import com.shop.zkmall.app.aspect.risk.RiskEvaluateRegistry;
import com.shop.zkmall.app.aspect.risk.evaluate.OrderNumEvaluate;
import com.shop.zkmall.app.aspect.risk.evaluate.PostSalesEvaluate;
import com.shop.zkmall.app.aspect.risk.evaluate.SkuEvaluate;
import com.shop.zkmall.app.aspect.risk.evaluate.WaitPayEvaluate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weicb
 */
@Configuration
public class RiskConfig {

    @Bean
    public RiskEvaluateRegistry riskEvaluateRegistry(OrderNumEvaluate eva1, PostSalesEvaluate eva2, SkuEvaluate eva3, WaitPayEvaluate eva4) {
        RiskEvaluateRegistry registry = new RiskEvaluateRegistry();
        registry.addEvaluate(eva1);
        registry.addEvaluate(eva2);
        registry.addEvaluate(eva3);
        registry.addEvaluate(eva4);
        return registry;
    }

    @Bean
    public RiskCheckAspect riskCheckAspect(RiskCheckHandler riskCheckHandler) {
        RiskCheckAspect registry = new RiskCheckAspect(riskCheckHandler);
        return registry;
    }


}

