/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.common;

import lombok.Data;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class Page<T> {

    /**
     * 返回数据总数
     */
    private long total;

    /**
     * 封装数据对象
     */
    private List<T> list;

    public Page(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }
}
