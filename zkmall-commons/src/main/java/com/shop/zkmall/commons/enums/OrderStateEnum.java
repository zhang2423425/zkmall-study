/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author zkmall zkmallvip@163.com
 */
@Getter
public enum OrderStateEnum {
    // 订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消 6-待成团 7-待售后
    PENDING_PAY(1, "待付款"),
    PENDING_SEND(2, "待发货"),
    PENDING_RECEIVE(3, "待收货"),
    COMPLETED(4, "已完成"),
    CANCELLED(5, "已取消"),
    PENDING_GROUP(6, "待成团"),
    PENDING_AFTER(7, "待售后");

    private final Integer code;
    private final String msg;

    OrderStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        if (Objects.isNull(code)) {
            return "";
        }
        OrderStateEnum[] arr = OrderStateEnum.values();
        for (OrderStateEnum orderStateEnum : arr) {
            if (code.equals(orderStateEnum.getCode())) {
                return orderStateEnum.getMsg();
            }
        }
        return "";
    }
}
