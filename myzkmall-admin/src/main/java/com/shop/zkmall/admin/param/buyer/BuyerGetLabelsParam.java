/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取标签列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerGetLabelsParam", description = "获取标签列表请求")
public class BuyerGetLabelsParam {

    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;
}
