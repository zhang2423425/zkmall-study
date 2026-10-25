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
 * 贴标签请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerSaveUserLabelParam", description = "贴标签请求")
public class BuyerBlackListParam {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    @Schema(description = "是否加入黑名单 1-是 0-否")
    private Integer ifBlack;
}
