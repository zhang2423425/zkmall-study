/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.credit;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("修改积分的参数")
public class UpdateCreditParam {

    @Schema(description = "用户id")
    private Long buyerUserId;

    @Schema(description = "积分值 扣减积分则传负数")
    private Integer credit;
}
