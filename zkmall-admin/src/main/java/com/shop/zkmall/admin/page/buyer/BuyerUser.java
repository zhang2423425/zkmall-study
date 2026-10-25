/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.buyer;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerUser", description = "客户返回数据实体类")
public class BuyerUser {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long buyerUserId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 会员等级名称
     */
    @Schema(description = "会员等级名称")
    private String memberLevelName;

    /**
     * 积分
     */
    @Schema(description = "积分")
    private Integer credit;

    /**
     * 消费总额
     */
    @Schema(description = "消费总额")
    private BigDecimal total;

    /**
     * 购买次数
     */
    @Schema(description = "购买次数")
    private Integer buyers;

    /**
     * 最近消费时间
     */
    @Schema(description = "最近消费时间")
    private String time;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    private String createTime;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    @Schema(description = "是否加入黑名单 1-是 0-否")
    private Integer ifBlack;

    @Schema(description = "标签id列表")
    private List<Long> labelIds;

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

    /**
     * 注册来源
     */
    @Schema(description = "注册来源")
    private Integer terminal;

    /**
     * 注册渠道
     */
    @Schema(description = "注册渠道")
    private String channelCode;
}
