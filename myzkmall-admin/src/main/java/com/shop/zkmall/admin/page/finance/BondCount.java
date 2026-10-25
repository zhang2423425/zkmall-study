/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.finance;

import com.shop.zkmall.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 保证金统计数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BondCount", description = "保证金统计数据")
public class BondCount {

    /**
     * 保证金总额
     */
    @Schema(description = "保证金总额")
    private BigDecimal total;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private Page<ShopBond> page;
}
