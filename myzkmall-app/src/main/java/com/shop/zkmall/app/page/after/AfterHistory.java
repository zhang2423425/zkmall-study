/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.after;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 售后协商历史返回数据实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "AfterHistory", description = "售后协商历史返回数据实体")
public class AfterHistory {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String reason;

    /**
     * 操作人姓名
     */
    @Schema(description = "操作人姓名")
    private String name;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    private String time;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private List<String> images;

}
