/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.group;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopGroupUpdateParam", description = "修改商品分组请求")
public class ShopGroupUpdateParam implements Serializable {
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
     * 商品id数组
     */
    @Schema(description = "商品id数组")
    private List<Long> ids;

    /**
     * 满足条件 1-全部满足 2-任意满足
     */
    @Schema(description = "满足条件 1-全部满足 2-任意满足")
    private Integer condition;

    /**
     * 筛选条件数组
     */
    @Schema(description = "筛选条件数组")
    private List<GroupCondition> conditions;

}
