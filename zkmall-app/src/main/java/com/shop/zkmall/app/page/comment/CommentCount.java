/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.comment;

import com.shop.zkmall.commons.domain.common.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 我的评论数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CommentCount", description = "我的评论数据")
public class CommentCount {

    /**
     * 全部评价数量
     */
    @Schema(description = "全部评价数量")
    private Integer total;

    /**
     * 有图评价数量
     */
    @Schema(description = "全部评价数量")
    private Integer imageTotal;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private Page<SelfComment> page;
}
