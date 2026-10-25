/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 风控拦截处理
 *
 * @author zkmall zkmallvip@163.com
 */
public interface RiskCheckHandler {

    /**
     * 风控拦截判断，用户/ip是黑名单或者命中配置的风控规则，则需要拦截
     *
     * @param request 请求参数
     * @return 返回 true 代表不触发风控，返回 false 表示触发风控规则，禁止下单
     */
    boolean check(HttpServletRequest request, Object[] args);
}
