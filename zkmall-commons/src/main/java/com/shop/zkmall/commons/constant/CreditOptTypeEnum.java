/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * @author zkmall zkmallvip@163.com
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum CreditOptTypeEnum {

    POLITE_GIFT(1, 1, "支付有礼"),
    DAILY_SIGN_IN(2, 1, "每日签到"),
    ADMIN_ADD(3, 1, "管理员增加积分"),
    ADMIN_SUB(4, 2, "管理员扣减积分"),
    EXCHANGE_COUPON(5, 2, "积分兑换优惠券"),
    ORDER_DEDUCT(6, 2, "订单抵扣积分"),
    ORDER_ROLLBACK_CREDIT(7, 1, "订单积分回流"),
    PLACE_ORDER(8, 1, "下单增加积分");

    private final int optType;
    private final int recordType;
    private final String recordContent;

    CreditOptTypeEnum(int optType, int recordType, String recordContent) {
        this.optType = optType;
        this.recordType = recordType;
        this.recordContent = recordContent;
    }

}
