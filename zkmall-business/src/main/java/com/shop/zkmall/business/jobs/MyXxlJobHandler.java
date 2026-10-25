/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.jobs;

import com.shop.zkmall.business.timing.*;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyXxlJobHandler {

    @Autowired
    private ProductDownRunnable productDownRunnable;

    /**
     * 根据运营计划发送优惠券并且发送提醒消息
     * @param param
     * @return
     */
    @XxlJob("showCrowdSendCouponAndMessage")
    public ReturnT<String> showCrowdSendCouponAndMessage(String param) {
        log.info(" execute showCrowdSendCouponAndMessage");
        //operateInitializationRunnable.run();
        return ReturnT.SUCCESS;
    }

    /**
     * 更新各种活动状态为结束
     * @param param
     * @return
     */
    @XxlJob("updateActivityToEndState")
    public ReturnT<String> updateActivityToEndState(String param) {
        log.info(" execute updateActivityToEndState");
        //toolRunnable.run();
        return ReturnT.SUCCESS;
    }

    /**
     * 对客户进行人群分类
     * @param param
     * @return
     */
    @XxlJob("crowdClassify")
    public ReturnT<String> crowdClassify(String param) {
        log.info(" execute crowdClassify");
        //updateCrowdPersonRunnable.run();
        return ReturnT.SUCCESS;
    }

    /**
     * 自动修改场景营销状态
     * @param param
     * @return
     */
    @XxlJob("updateSceneState")
    public ReturnT<String> updateSceneState(String param) {
        log.info(" execute updateSceneState");
        //updateSceneStateRunnable.run();
        return ReturnT.SUCCESS;
    }


}