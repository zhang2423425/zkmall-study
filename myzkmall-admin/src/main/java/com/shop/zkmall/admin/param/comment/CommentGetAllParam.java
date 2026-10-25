/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.comment;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取评论列表请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "CommentGetAllParam", description = "获取评论列表请求")
public class CommentGetAllParam extends PageParam {

    /**
     * 是否敏感词审核  1-是 0-否
     */
    @Schema(description = "是否敏感词审核  1-是 0-否")
    private Integer ifSensitive;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String shopName;

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private String shopCode;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String productId;

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    private String keyword;
}
