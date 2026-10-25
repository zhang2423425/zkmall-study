/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取买家信息请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GetBuyerParam", description = "获取买家信息请求")
public class AfterGetBuyerParam {

    /**
     * 买家账户id
     */
    @Schema(description = "买家账户id")
    private Long buyerUserId;
}
