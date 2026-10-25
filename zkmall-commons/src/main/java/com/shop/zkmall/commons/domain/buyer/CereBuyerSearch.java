/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_search 客户历史搜索实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereBuyerSearch", description = "客户历史搜索实体")
public class CereBuyerSearch implements Serializable {
    /**
     * 搜索id
     */
    @Schema(description = "搜索id")
    private Long searchId;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 搜索内容
     */
    @Schema(description = "搜索内容")
    private String search;

}
