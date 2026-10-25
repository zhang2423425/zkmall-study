/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_canvas 画布信息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("画布信息实体")
public class CerePlatformCanvasPage implements Serializable {
    /**
     * 画布id
     */
    @Schema(description = "画布id")
    private Long canvasId;

    /**
     * 终端 1-小程序 2-H5 3-APP 4-PC
     */
    @Schema(description = "终端 1-小程序 2-H5 3-APP 4-PC")
    private Integer terminal;

    /**
     * 画布json数据
     */
    @Schema(description = "画布json数据")
    private String json;

    /**
     * 类型 1-系统相关页面 2-自定义页面 3-店铺装修
     */
    @Schema(description = "类型 1-系统相关页面 2-自定义页面 3-店铺装修")
    private Integer type;

    /**
     * 页面名称
     */
    @Schema(description = "页面名称")
    private String name;

    @Schema(description = "是否首页的标识")
    private Boolean homePageFlag;

    @Schema(description = "固定的页面")
    private Boolean defaultPage;

    @Schema(description = "封面图")
    private String coverLogo;

    @Schema(description = "关联的模板id")
    private Long templateId;

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
