/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.compose;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 组合捆绑列表
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopCompose", description = "组合捆绑列表")
public class ShopCompose {

    /**
     * 组合id
     */
    @Schema(description = "组合id")
    private Long composeId;

    /**
     * 组合名称
     */
    @Schema(description = "组合名称")
    private String composeName;

    /**
     * 活动起始时间
     */
    @Schema(description = "活动起始时间")
    private String time;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer number;

    /**
     * 定价类型
     */
    @Schema(description = "定价类型")
    private Integer composeType;

    /**
     * 状态 0-未开始 1-进行中 2-已结束 3-已停用
     */
    @Schema(description = "状态 0-未开始 1-进行中 2-已结束 3-已停用")
    private Integer state;
}
