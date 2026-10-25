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
public enum LongEnum {

    ROOT("根节点", 0L),
    ZERO("0", 0L),
    PLATFORM("平台端权限或角色", 1L),
    EXPRESS_100("快递100", 7L),
    EXPRESS_NIAO("快递鸟", 8L);

    final String name;
    final Long code;


    private static final Map<String, LongEnum> VALUE_MAP = new HashMap<>();

    static {
        for (LongEnum gender : LongEnum.values()) {
            VALUE_MAP.put(gender.name, gender);
        }
    }

    LongEnum(String name, Long code) {
        this.code = code;
        this.name = name;
    }

    public static Long getByName(String name) {
        LongEnum result = VALUE_MAP.get(name);
        if (result == null) {
            throw new IllegalArgumentException("No element matches " + name);
        }
        return result.code;
    }
}
