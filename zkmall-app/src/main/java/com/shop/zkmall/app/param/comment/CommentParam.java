/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.comment;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取评价列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CommentParam", description = "获取评价列表请求")
public class CommentParam extends PageParam {

    /**
     * 是否有图 1-是 0-全部
     */
    @Schema(description = "是否有图 1-是 0-全部")
    private String state;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 关键字搜索
     */
    @Schema(description = "关键字搜索")
    private String search;

    /**
     * 评价类型 1-待评价 2-待追评 3-已评价
     */
    @Schema(description = "评价类型 1-待评价 2-待追评 3-已评价")
    private Integer type;
}
