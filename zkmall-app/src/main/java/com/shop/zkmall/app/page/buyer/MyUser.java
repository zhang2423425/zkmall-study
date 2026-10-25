/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.buyer;

import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 我的信息
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "MyUser", description = "我的信息")
public class MyUser extends CereBuyerUser {

    /**
     * 未读取消息总数
     */
    @Schema(description = "未读取消息总数")
    private Integer notRead;

    /**
     * 未读回复数
     */
    @Schema(description = "未读回复数")
    private Integer unreadReply;

    /**
     * 待付款订单数
     */
    @Schema(description = "待付款订单数")
    private Integer waitPayOrderCount;

    /**
     * 待发货订单数
     */
    @Schema(description = "待发货订单数")
    private Integer waitSendOrderCount;

    /**
     * 待收货订单数
     */
    @Schema(description = "待收货订单数")
    private Integer waitReceiveOrderCount;

    /**
     * 总订单数
     */
    @Schema(description = "总订单数")
    private Integer totalOrderCount;


    /**
     * 累计积分
     */
    @Schema(description = "累计积分")
    private Integer totalCredit;

    /**
     * 售后数量
     */
    @Schema(description = "售后数量")
    private Integer afterCount;

    /**
     * 收藏数量
     */
    @Schema(description = "收藏数量")
    private Long collectCount;

    /**
     * 问答数量
     */
    @Schema(description = "问答数量")
    private Long askAnswerCount;

    /**
     * 评价数量
     */
    @Schema(description = "评价数量")
    private Long commentCount;

    /**
     * 足迹数量
     */
    @Schema(description = "足迹数量")
    private Long visitCount;

    /**
     * 优惠券数量(平台券)
     */
    @Schema(description = "优惠券数量(平台券)")
    private Integer platformCouponCount;

    /**
     * 卡券数量
     */
    @Schema(description = "卡券数量(商家券)")
    private Integer shopCouponCount;
}
