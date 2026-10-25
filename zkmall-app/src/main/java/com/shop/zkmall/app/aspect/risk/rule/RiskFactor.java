/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk.rule;

/**
 * 风控因子
 *
 * @author zkmall zkmallvip@163.com
 */
public enum RiskFactor {

    ORDER_NUM("orderNum"),
    WAIT_PAY_NUM("waitPayNum"),
    SKU_NUM("skuNum"),
    POST_NUM("postNum");

    private String name;

    RiskFactor(String name) {
        this.name = name;
    }

}
