/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 评论返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopComment", description = "评论返回数据实体类")
public class ShopComment {

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
     * 评论人
     */
    @Schema(description = "评论人")
    private String name;

    /**
     * 评论日期
     */
    @Schema(description = "评论日期")
    private String createTime;

    /**
     * 是否隐藏 1-是 0-否
     */
    @Schema(description = "是否隐藏 1-是 0-否")
    private Integer state;
}
