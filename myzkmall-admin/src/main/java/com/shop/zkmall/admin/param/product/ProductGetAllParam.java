/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.product;

import com.shop.zkmall.commons.domain.common.PageParam;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 获取商品列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "ProductGetAllParam", description = "获取商品列表请求")
public class ProductGetAllParam extends PageParam {

    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    private String shopName;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String productId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品状态 0-已下架 1-已上架 2-待审核 3-审核失败
     */
    @Schema(description = "商品状态 0-已下架 1-已上架 2-待审核 3-审核失败")
    private Integer shelveState;

    /**
     * 创建时间数组
     */
    @Schema(description = "下单时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if (!EmptyUtils.isEmpty(dates) && dates.size() > 1) {
            this.startTime = dates.get(0);
            this.endTime = dates.get(1);
        }
    }

    /**
     * 创建开始时间
     */
    @Schema(description = "发送开始时间")
    private String startTime;

    /**
     * 创建结束时间
     */
    @Schema(description = "发送结束时间")
    private String endTime;

    /**
     * 关联分类id
     */
    @Schema(description = "关联分类id")
    private Long classifyId;

    /**
     * 分类层级
     */
    @Schema(description = "分类层级")
    private Integer classifyLevel;
}
