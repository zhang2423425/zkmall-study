/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.jobs;

import com.shop.zkmall.admin.timing.LabelInitializationRunnable;
import com.shop.zkmall.admin.timing.ShopInitializationRunnable;
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

    private final LabelInitializationRunnable labelInitializationRunnable;

    private final ShopInitializationRunnable shopInitializationRunnable;

    @XxlJob("automaticUpgrade")
    public ReturnT<String> automaticUpgrade(String param) {
        log.info(" execute automaticUpgrade");
        //distributionLevelInitializationRunnable.run();
        return ReturnT.SUCCESS;
    }

    @XxlJob("labelBuyerUser")
    public ReturnT<String> labelBuyerUser(String param) {
        log.info(" execute labelBuyerUser");
        labelInitializationRunnable.run();
        return ReturnT.SUCCESS;
    }

    @XxlJob("stopShop")
    public ReturnT<String> stopShop(String param) {
        log.info(" execute stopShop");
        shopInitializationRunnable.run();
        return ReturnT.SUCCESS;
    }


}
