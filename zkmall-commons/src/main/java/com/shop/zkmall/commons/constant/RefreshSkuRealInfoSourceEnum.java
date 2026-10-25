/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
@Getter
public enum RefreshSkuRealInfoSourceEnum implements Serializable {

    NORMAL(0, "完整刷新"),
    PUT_ON_SHELVE(1, "商品上架"),
    PUT_OFF_SHELVE(2, "商品下架"),
    EDIT_PRODUCT(3, "商品编辑"),
    GROUP_PRE_HOT(4, "拼团活动预热"),
    GROUP_START(5, "拼团活动开始"),
    GROUP_END(6, "拼团活动结束"),
    SHOP_SECKILL_PRE_HOT(7, "商家秒杀预热"),
    SHOP_SECKILL_START(8, "商家秒杀开始"),
    SHOP_SECKILL_END(9, "商家秒杀结束"),
    SHOP_DISCOUNT_PRE_HOT(10, "商家折扣预热"),
    SHOP_DISCOUNT_START(11, "商家折扣开始"),
    SHOP_DISCOUNT_END(12, "商家折扣结束"),
    SECKILL_START(13, "平台秒杀开始"),
    SECKILL_END(14, "平台秒杀结束"),
    DISCOUNT_START(15, "平台折扣开始"),
    DISCOUNT_END(16, "平台折扣结束"),
    SCENE_START(17, "场景营销开始"),
    SCENE_END(18, "场景营销结束"),
    EDIT_MEMBER_PRODUCT(19, "编辑会员价"),
    CLEAR_MEMBER_PRODUCT(20, "清除会员价"),
    EDIT_FICTITIOUS_NUMBER(21, "编辑虚拟销量");

    final int code;
    final String name;

    private static final Map<Integer, RefreshSkuRealInfoSourceEnum> CODE_MAP = new HashMap<>();
    private static final Map<String, RefreshSkuRealInfoSourceEnum> VALUE_MAP = new HashMap<>();

    static {
        for (RefreshSkuRealInfoSourceEnum sourceEnum : RefreshSkuRealInfoSourceEnum.values()) {
            CODE_MAP.put(sourceEnum.getCode(), sourceEnum);
            VALUE_MAP.put(sourceEnum.name, sourceEnum);
        }
    }

    RefreshSkuRealInfoSourceEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static int getByName(String name) {
        RefreshSkuRealInfoSourceEnum result = VALUE_MAP.get(name);
        if (result == null) {
            throw new IllegalArgumentException("No element matches " + name);
        }
        return result.code;
    }

    public static RefreshSkuRealInfoSourceEnum getByCode(int code) {
        RefreshSkuRealInfoSourceEnum result = CODE_MAP.get(code);
        if (result == null) {
            throw new IllegalArgumentException("No element matches " + code);
        }
        return result;
    }
}
