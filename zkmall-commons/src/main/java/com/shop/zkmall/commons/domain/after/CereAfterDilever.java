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
 * cere_after_dilever 买家退货信息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereAfterDilever implements Serializable {
    /**
     * 关联订单id
     */
    @Schema(description = "关联订单id")
    private Long orderId;

    /**
     * 关联售后单id
     */
    @Schema(description = "关联售后单id")
    private Long afterId;

    /**
     * 快递公司（取数据字典）
     */
    @Schema(description = "快递公司（取数据字典）")
    private Long express;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String deliverFormid;

    /**
     * 说明
     */
    @Schema(description = "说明")
    private String reason;

    /**
     * 凭证图片
     */
    @Schema(description = "凭证图片")
    private String image;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
