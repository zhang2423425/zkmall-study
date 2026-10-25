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
public enum PaymentModeEnum {
    // 支付方式 1-微信支付
    WX_PAY(1, "微信支付"),
    ZFB_PAY(2, "支付宝支付");

    private final Integer code;
    private final String msg;

    PaymentModeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        if (Objects.isNull(code)) {
            return "";
        }
        PaymentModeEnum[] arr = PaymentModeEnum.values();
        for (PaymentModeEnum paymentModeEnum : arr) {
            if (code.equals(paymentModeEnum.getCode())) {
                return paymentModeEnum.getMsg();
            }
        }
        return "";
    }
}
