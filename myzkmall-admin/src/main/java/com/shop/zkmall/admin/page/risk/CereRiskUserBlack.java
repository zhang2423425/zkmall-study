/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.risk;

import com.shop.zkmall.commons.domain.risk.CereRiskBlack;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户黑名单分页对象")
public class CereRiskUserBlack extends CereRiskBlack {

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "微信openId")
    private String openId;

    @Schema(description = "手机号")
    private String phone;

}
