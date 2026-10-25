/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_product_classify 平台商品分类实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereProductClassify", description = "平台商品分类实体类")
public class CereProductClassify implements Serializable {

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    @TableId(type = IdType.AUTO)
    private Long classifyId;

    /**
     * 分类父id
     */
    @Schema(description = "分类父id")
    private Long classifyPid;

    /**
     * 分类层级结构名称
     */
    @Schema(description = "分类层级结构名称")
    private String classifyHierarchy;

    /**
     * 分类级别
     */
    @Schema(description = "分类级别")
    private Integer classifyLevel;

    /**
     * 分类层级结构
     */
    @Schema(description = "分类层级结构")
    private String classifyLevelHierarchy;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String classifyName;

    /**
     * 分类图片地址
     */
    @Schema(description = "分类图片地址")
    private String classifyImage;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sort;

    /**
     * 标签
     */
    @Schema(description = "标签")
    private Long link;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;

}
