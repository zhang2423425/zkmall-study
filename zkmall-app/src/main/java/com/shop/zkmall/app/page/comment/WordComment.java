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
 * 关键词评论数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "WordComment", description = "关键词评论数据")
public class WordComment {

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
     * 追加图片地址（多个以逗号隔开）
     */
    private String addImage;

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
     * 评论时间
     */
    @Schema(description = "评论时间")
    private String createTime;

    /**
     * 追评时间
     */
    @Schema(description = "追评时间")
    private String addTime;

    /**
     * 追评天数
     */
    @Schema(description = "追评天数")
    private Integer day;
}
