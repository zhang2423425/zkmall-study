/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 获取评价详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CommentIdParam", description = "获取评价详情请求")
public class CommentIdParam {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;
}
