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
 * 评论数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Comment", description = "评论数据")
public class Comment {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;

    /**
     * 图片地址（多个以逗号隔开）
     */
    private String image;

    /**
     * 图片数组
     */
    @Schema(description = "图片数组")
    private List<String> images;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String comment;

    /**
     * 商品印象
     */
    @Schema(description = "商品印象")
    private String impression;

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
}
