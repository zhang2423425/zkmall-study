/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.timing;

import com.shop.zkmall.admin.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户标签定时任务处理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShopInitializationRunnable {

    private final CerePlatformShopService cerePlatformShopService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有可用商家数据
            List<CerePlatformShop> shops = cerePlatformShopService.findAll();
            String today = TimeUtils.yyMMddHHmmss();
            if (!EmptyUtils.isEmpty(shops)) {
                for (CerePlatformShop shop : shops) {
                    //计算合同有效截止时间
                    String time = TimeUtils.getMoreYearAfter(shop.getEffectiveDate(), shop.getEffectiveYear());
                    //判断合同有效期是否过期
                    if (TimeUtils.compareTo(today, time + " 00:00:00")) {
                        //如果当前时间大于有效截止时间,修改合同有效状态和店铺禁用
                        shop.setContractState(IntegerEnum.NO.getCode());
                        shop.setState(IntegerEnum.NO.getCode());
                        shop.setUpdateTime(today);
                        cerePlatformShopService.updateShopStop(shop);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
