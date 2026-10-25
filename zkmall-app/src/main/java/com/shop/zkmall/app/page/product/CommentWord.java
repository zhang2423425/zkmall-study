/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.product;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 评论关键词数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CommentWord", description = "评论关键词数据")
public class CommentWord {

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    private String keyWord;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Integer count;
}
