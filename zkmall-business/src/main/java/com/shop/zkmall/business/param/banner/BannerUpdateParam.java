/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.param.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 更新banner请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BannerUpdateParam", description = "更新banner请求")
public class BannerUpdateParam {

    /**
     * bannerid
     */
    @Schema(description = "bannerid")
    private Long bannerId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 样式
     */
    @Schema(description = "样式")
    private Integer bannerStyle;

    /**
     * 图片数组
     */
    @Schema(description = "图片数组")
    private List<String> images;

    /**
     * 跳转地址
     */
    @Schema(description = "跳转地址")
    private String bannerUrl;
}
