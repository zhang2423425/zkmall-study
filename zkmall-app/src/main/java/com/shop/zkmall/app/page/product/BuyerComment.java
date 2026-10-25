/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 商品评论数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerComment", description = "商品评论数据")
public class BuyerComment {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

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
    private Integer ifLike = 0;

    /**
     * 点赞人数
     */
    @Schema(description = "点赞人数")
    private Integer likes;

}
