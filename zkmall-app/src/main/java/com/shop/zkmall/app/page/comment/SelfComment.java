/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 我的评论数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerComment", description = "商品评论数据")
public class SelfComment {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品售价
     */
    @Schema(description = "商品售价")
    private String productPrice;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String productImage;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String name;

    /**
     * 客户头像
     */
    @Schema(description = "客户头像")
    private String headImage;

    /**
     * 图片数据
     */
    private String image;

    /**
     * 追加图片数据
     */
    private String addImage;

    /**
     * 图片数据数组
     */
    @Schema(description = "图片数据")
    private List<String> images;

    /**
     * 追加图片数组
     */
    @Schema(description = "图片数据")
    private List<String> addImages;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String comment;

    /**
     * 追加评论内容
     */
    @Schema(description = "追加评论内容")
    private String addComment;

    /**
     * 追评时间
     */
    private String addTime;

    /**
     * 评论时间
     */
    @Schema(description = "评论时间")
    private String createTime;

    /**
     * 规格值
     */
    private String value;

    /**
     * 规格值数组
     */
    @Schema(description = "规格值数组")
    private List<String> values;

    /**
     * 是否点赞 1-是 0-否
     */
    @Schema(description = "是否点赞 1-是 0-否")
    private Integer ifLike;

    /**
     * 点赞人数
     */
    @Schema(description = "点赞人数")
    private Integer likes;

    /**
     * 是否追评 1-是 0-否
     */
    @Schema(description = "是否追评 1-是 0-否")
    private Integer ifAdd;

    /**
     * 商品满意度(宝贝描述) 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "商品满意度(宝贝描述) 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer star;

    /**
     * 描述相符 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "描述相符 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer des;

    /**
     * 物流服务 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "物流服务 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer delivery;

    /**
     * 服务态度 1-一星 2-二星 3-三星 4-四星 5-五星
     */
    @Schema(description = "服务态度 1-一星 2-二星 3-三星 4-四星 5-五星")
    private Integer attitude;

    /**
     * 商品印象
     */
    @Schema(description = "商品印象")
    private String impression;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private String shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String shopLogo;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String productId;

    /**
     * 规格id
     */
    @Schema(description = "规格id")
    private String skuId;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private Long orderId;
}
