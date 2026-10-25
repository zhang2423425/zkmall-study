/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.group;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分组列表数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Group", description = "分组列表数据")
public class Group {
    /**
     * 商品分组id
     */
    @Schema(description = "商品分组id")
    private Long shopGroupId;

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;

    /**
     * 分组描述
     */
    @Schema(description = "分组描述")
    private String groupDescribe;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Integer number;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private String updateTime;
}
