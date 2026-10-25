/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order.placeOrderTemplate;

import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PlaceOrderFactory {

    private final NormalPlaceOrder normalPlaceOrder;

    /**
     * 获取指定 下单方法
     *
     * @param type /
     */
    public PlaceOrderTemplate getPlaceOrderTemplate(Integer type) {

        if (EmptyUtils.isEmpty(type)
                || IntegerEnum.SECKILL_SUBMIT.getCode().equals(type)
                || IntegerEnum.DISCOUNT_SUBMIT.getCode().equals(type)) {
            //正常下单
            return normalPlaceOrder;
        }
        return null;
    }
}
