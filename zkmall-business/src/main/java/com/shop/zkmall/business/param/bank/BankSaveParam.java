/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.bank;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 绑定账户请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BankSaveParam", description = "绑定账户请求")
public class BankSaveParam {

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 持卡人姓名
     */
    @Schema(description = "持卡人姓名")
    @NotBlank(message = "持卡人姓名不能为空")
    private String cardName;

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String cardNumber;

    /**
     * 所属银行（取数据字典）
     */
    @Schema(description = "所属银行（取数据字典）")
    @NotNull(message = "所属银行不能为空")
    private Long bank;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;
}
