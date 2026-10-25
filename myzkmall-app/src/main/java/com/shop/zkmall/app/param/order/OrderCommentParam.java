/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.order;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 评论请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "OrderCommentParam", description = "评论请求")
public class OrderCommentParam {

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private Long skuId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private Long productId;

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;

    /**
     * 图片地址（多个以逗号隔开）
     */
    @Schema(description = "图片地址（多个以逗号隔开）")
    private String image;

    /**
     * 评论
     */
    @Schema(description = "评论")
    private String comment;

    /**
     * 商品满意度 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "商品满意度 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer star;

    /**
     * 描述相符 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "商品满意度 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer des;

    /**
     * 物流服务 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "商品满意度 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer delivery;

    /**
     * 服务态度 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "商品满意度 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer attitude;

    /**
     * 商品印象
     */
    @Schema(description = "商品印象")
    private String impression;
}
