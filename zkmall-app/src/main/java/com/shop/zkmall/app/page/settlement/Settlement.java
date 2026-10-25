/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.settlement;

import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 结算查询返回数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Settlement", description = "结算查询返回数据")
public class Settlement {

    /**
     * 收货地址信息
     */
    @Schema(description = "收货地址信息")
    private CereBuyerReceive receive;

    /**
     * 商品明细数据
     */
    @Schema(description = "商品明细数据")
    private List<SettlementShop> shops;

    /**
     * 置灰商品数据
     */
    @Schema(description = "商品明细数据")
    private List<SettlementShop> invalidShops;

    /**
     * 花呗手续费支付方式 1-商户支付 2-用户支付
     */
    @Schema(description = "花呗手续费支付方式 1-商户支付 2-用户支付")
    private Integer huabeiChargeType;

    /**
     * 花呗手续费比例列表 3期 6期 12期
     */
    @Schema(description = "花呗手续费比例列表 3期 6期 12期")
    private List<Double> huabeiFeerateList;

    @Schema(description = "用户总积分")
    private Integer userTotalCredit;

    @Schema(description = "每个sku可以抵扣多少积分")
    private Map<Long, Integer> skuCreditMap;

    @Schema(description = "每单满足多少金额才能抵扣积分")
    private BigDecimal orderCreditThreshold;

    @Schema(description = "每单最多抵扣多少积分")
    private Integer creditDeductLimit;

}
