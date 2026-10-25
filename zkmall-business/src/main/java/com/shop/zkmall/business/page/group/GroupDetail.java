/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.group;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 分组详情数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "GroupDetail", description = "分组详情数据")
public class GroupDetail {
    /**
     * 商品分组id
     */
    @Schema(description = "商品分组id")
    private Long shopGroupId;

    /**
     * 关联店铺id
     */
    @Schema(description = "关联店铺id")
    private Long shopId;

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 分组封面图片地址
     */
    @Schema(description = "分组封面图片地址")
    private String groupImage;

    /**
     * 分组描述
     */
    @Schema(description = "分组描述")
    private String groupDescribe;

    /**
     * 关联商品数据
     */
    @Schema(description = "关联商品数据")
    private List<GroupProduct> products;
}
