/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereProductParameter {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品key
     */
    private String parameterKey;

    /**
     * 商品value
     */
    private String parameterValue;

    /**
     * 创建时间
     */
    private String createTime;

}
