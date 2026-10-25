/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum WxPayEnum {

    PAY_TYPE_JSPAPI("微信支付", "JSAPI"),
    PAY_TYPE_NATIVE("微信扫码支付", "NATIVE"),
    PAY_SUCCESS("支付成功", "SUCCESS"),
    REFUND_SUCCESS("退款成功", "OK"),
    REFUND_OK("退款接口请求成功", "SUCCESS"),
    REFUND_PROCESSING("退款处理中", "PROCESSING"),
    BUSINESS_BALANCE_NOTENOUGH("商户可用余额不足", "NOTENOUGH"),
    REFUND_ERROR("微信扫码支付", "NATIVE"),
    NOT_ENOUGH_V3("可用余额不足", "NOT_ENOUGH");

    final String name;
    final String code;

    private static final Map<String, WxPayEnum> VALUE_MAP = new HashMap<>();

    static {
        for (WxPayEnum gender : WxPayEnum.values()) {
            VALUE_MAP.put(gender.name, gender);
        }
    }

    WxPayEnum(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public static String getByName(String name) {
        WxPayEnum result = VALUE_MAP.get(name);
        if (result == null) {
            throw new IllegalArgumentException("No element matches " + name);
        }
        return result.code;
    }
}
