/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 评论详情返回数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CommentDetail", description = "评论详情返回数据")
public class CommentDetail {

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
     * 追加图片
     */
    @Schema(description = "追加图片")
    private String addImage;

    /**
     * 评论
     */
    @Schema(description = "评论")
    private String comment;

    /**
     * 追加评论
     */
    @Schema(description = "追加评论")
    private String addComment;

    /**
     * 图片数组
     */
    @Schema(description = "图片数组")
    private List<String> images;

    /**
     * 追加图片数组
     */
    @Schema(description = "追加图片数组")
    private List<String> addImages;

    /**
     * 敏感词
     */
    @Schema(description = "敏感词")
    private String sensitiveWord;

    /**
     * 追评时间差
     */
    @Schema(description = "追评时间差")
    private Integer time;

    /**
     * 评论时间
     */
    private String createTime;

    /**
     * 追评时间
     */
    private String addTime;
}
