/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新评论请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CommentUpdateParam", description = "更新评论请求")
public class CommentUpdateParam {

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    private Long commentId;

    /**
     * 是否隐藏 1-是 0-否
     */
    @Schema(description = "是否隐藏 1-是 0-否")
    private Integer state;
}
