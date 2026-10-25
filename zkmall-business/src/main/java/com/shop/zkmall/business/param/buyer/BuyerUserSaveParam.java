/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 加标签
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerUserSaveParam", description = "加标签请求")
public class BuyerUserSaveParam {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 标签id数组
     */
    @Schema(description = "标签id数组")
    private List<Long> labelIds;
}
