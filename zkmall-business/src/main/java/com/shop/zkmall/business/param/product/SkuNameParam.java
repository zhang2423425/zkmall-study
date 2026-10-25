/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.product;

import com.shop.zkmall.commons.constant.IntegerEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

/**
 * 商品规格名和值级别数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "SkuNameParam", description = "商品规格名和值级别数据")
public class SkuNameParam {

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 是否需要配图 1-是 0-否
     */
    private Integer need;

    public void setNeed(Integer need) {
        this.needImg = IntegerEnum.YES.getCode().equals(need);
    }

    /**
     * 是否需要配图
     */
    private Boolean needImg;

    /**
     * 规格名级别
     */
    @Schema(description = "规格名级别")
    private String code;

    /**
     * 规格名
     */
    @Schema(description = "规格名")
    private String skuName;

    /**
     * 规格值数据
     */
    @Schema(description = "规格值数据")
    @Valid
    private List<SkuValueParam> values;

}
