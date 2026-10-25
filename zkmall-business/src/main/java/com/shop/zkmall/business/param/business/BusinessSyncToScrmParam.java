/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.business;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * scrm同步数据的参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BusinessSyncToScrmParam", description = "scrm同步数据的参数")
public class BusinessSyncToScrmParam {

    /**
     * 商户编码
     */
    @Schema(description = "商户编码")
    private String shopCode;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 秘钥
     */
    @Schema(description = "秘钥")
    private String secret;

    /**
     * 上个数据的id
     */
    private Long lastId;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 可以代表订单id或者商品id等其他业务id
     */
    private Long businessId;
}
