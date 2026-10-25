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
public enum AfterTypeEnum {
    // 售后类型  1-仅退款  2-退货退款
    ONLY_REFUND(1, "仅退款 "), REFUNDS(2, "退货退款");

    private final Integer code;
    private final String msg;

    AfterTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        if (Objects.isNull(code)) {
            return "";
        }
        AfterTypeEnum[] arr = AfterTypeEnum.values();
        for (AfterTypeEnum afterTypeEnum : arr) {
            if (code.equals(afterTypeEnum.getCode())) {
                return afterTypeEnum.getMsg();
            }
        }
        return "";
    }
}
