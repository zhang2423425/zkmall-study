/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.jobs;

import com.shop.zkmall.app.service.product.CereSkuMemberRealInfoService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyXxlJobHandler {

    private final CereSkuMemberRealInfoService cereSkuMemberRealInfoService;

    @XxlJob("refreshAllProduct")
    public ReturnT<String> refreshAllProduct(String param) {
        log.info(" execute refreshAllProduct");
        long start = System.currentTimeMillis();
        cereSkuMemberRealInfoService.refreshAllProductRealInfo();
        log.info("refreshAllProduct use {}", (System.currentTimeMillis() - start) / 1000);
        return ReturnT.SUCCESS;
    }
}
