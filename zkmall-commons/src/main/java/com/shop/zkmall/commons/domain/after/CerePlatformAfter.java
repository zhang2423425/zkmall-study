/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.after;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_after 平台介入售后信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CerePlatformAfter implements Serializable {
    /**
     * 关联订单id
     */
    @Schema(description = "关联订单id")
    private Long orderId;

    /**
     * 问题描述
     */
    private String reason;

    /**
     * 举证图片
     */
    private String image;

    /**
     * 备注
     */
    private String remark;

    /**
     * 处理状态 0-未处理 1-同意 2-拒绝
     */
    @Schema(description = "处理状态 0-未处理 1-同意 2-拒绝")
    private Integer state;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 平台处理时间
     */
    private String handleTime;

}
