/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户列表
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerUser", description = "客户列表数据")
public class BuyerUser {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 客户昵称
     */
    @Schema(description = "客户昵称")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 消费次数
     */
    @Schema(description = "消费次数")
    private Integer frequency;

    /**
     * 累计消费金额
     */
    @Schema(description = "累计消费金额")
    private BigDecimal total;

    /**
     * 上次消费时间
     */
    @Schema(description = "上次消费时间")
    private String lastTime;

    /**
     * 成为客户时间
     */
    @Schema(description = "成为客户时间")
    private String time;

    /**
     * 客户关联标签
     */
    @Schema(description = "客户关联标签")
    private String labelName;

    /**
     * 标签id数组
     */
    @Schema(description = "标签id数组")
    private List<Long> labelIds;

    /**
     * 标签id拼接字符串
     */
    private String ids;

    /**
     * 注册ip
     */
    @Schema(description = "注册ip")
    private String registerIp;

    /**
     * 上次登录的ip
     */
    @Schema(description = "上次登录的ip")
    private String lastLoginIp;
}
