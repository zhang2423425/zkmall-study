/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.buyer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_receive 客户收货地址信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereBuyerReceive", description = "客户收货地址信息实体类")
public class CereBuyerReceive implements Serializable {

    /**
     * 收货地址id
     */
    @Schema(description = "收货地址id")
    @TableId(type = IdType.AUTO)
    private Long receiveId;

    /**
     * 关联客户id
     */
    @Schema(description = "关联客户id")
    private Long buyerUserId;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    private String receiveName;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    private String receivePhone;

    /**
     * 收货地址
     */
    @Schema(description = "收货地址")
    private String receiveAdress;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String address;

    /**
     * 地址标签
     */
    @Schema(description = "地址标签")
    private String label;

    /**
     * 是否默认 1-是 0-否
     */
    @Schema(description = "是否默认 1-是 0-否")
    private Integer ifDefault;

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
