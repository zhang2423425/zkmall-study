/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.product;

import com.shop.zkmall.commons.constant.RefreshSkuRealInfoSourceEnum;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class RefreshRealInfoDTO {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 刷新操作来源
     */
    private RefreshSkuRealInfoSourceEnum sourceEnum;

    /**
     * 虚拟销量
     */
    private Integer fictitiousNumber;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 校验码
     */
    private String verifyCode;
}
