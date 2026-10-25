/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 收款码数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "PayUrl", description = "收款码")
public class PayUrl {

    /**
     * 收款码地址
     */
    @Schema(description = "收款码地址")
    private String url;

    /**
     * （可能是父订单id，也可能是子订单id）
     */
    @Schema(description = "（可能是父订单id，也可能是子订单id）")
    private Long orderId;

    /**
     * 拼单id
     */
    @Schema(description = "拼单id")
    private Long collageId;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal money;

    /**
     * 支付结果
     */
    @Schema(description = "支付结果")
    private String code;
}
