/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.buyer;

import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户详情返回数据实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerUserDetail", description = "客户详情返回数据实体类")
public class BuyerUserDetail {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private Long buyerUserId;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String name;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String headImage;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private String birthday;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    private String createTime;

    @Schema(description = "标签id列表")
    private List<Long> labelIds;

    /**
     * 标签数据
     */
    @Schema(description = "标签数据")
    private List<String> labels;

    /**
     * 下单数
     */
    @Schema(description = "下单数")
    private Integer orders;

    /**
     * 支付成功数
     */
    @Schema(description = "支付成功数")
    private Integer pays;

    /**
     * 购买商品数
     */
    @Schema(description = "购买商品数")
    private Integer products;

    /**
     * 累计消费额
     */
    @Schema(description = "累计消费额")
    private BigDecimal price;

    /**
     * 售后次数
     */
    @Schema(description = "售后次数")
    private Integer afters;

    /**
     * 售后单数
     */
    @Schema(description = "售后单数")
    private Integer afterOrders;

    /**
     * 售后成功单数
     */
    @Schema(description = "售后成功单数")
    private Integer successAfters;

    /**
     * 订单列表
     */
    @Schema(description = "订单列表")
    private List<BuyerOrder> orderList;

    /**
     * 评论列表
     */
    @Schema(description = "评论列表")
    private List<BuyerComment> comments;

    /**
     * 收货地址列表
     */
    @Schema(description = "收货地址列表")
    private List<CereBuyerReceive> receives;
}
