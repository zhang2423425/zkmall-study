/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.page.buyer;

import com.shop.zkmall.commons.poi.export.ExcelResources;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class BuyerUserExportDTO {

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @ExcelResources(order = 0, title = "客户id", cloumn = 15)
    private Long buyerUserId;

    /**
     * 客户昵称
     */
    @Schema(description = "客户昵称")
    @ExcelResources(order = 1, title = "客户昵称", cloumn = 15)
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelResources(order = 2, title = "手机号", cloumn = 15)
    private String phone;

    /**
     * 会员等级
     */
    @Schema(description = "会员等级")
    @ExcelResources(order = 3, title = "会员等级", cloumn = 15)
    private String memberLevelName;

    /**
     * 消费总额
     */
    @Schema(description = "消费总额")
    @ExcelResources(order = 4, title = "消费总额", cloumn = 15)
    private BigDecimal total;

    /**
     * 购买次数
     */
    @Schema(description = "购买次数")
    @ExcelResources(order = 5, title = "购买次数", cloumn = 15)
    private Integer buyers;

    /**
     * 上次消费时间
     */
    @Schema(description = "上次消费时间")
    @ExcelResources(order = 6, title = "上次消费时间", cloumn = 15)
    private String time;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    @ExcelResources(order = 7, title = "注册时间", cloumn = 15)
    private String createTime;

    /**
     * 注册ip
     */
    @Schema(description = "注册ip")
    @ExcelResources(order = 8, title = "注册ip", cloumn = 15)
    private String registerIp;

    /**
     * 最后登录ip
     */
    @Schema(description = "最后登录ip")
    @ExcelResources(order = 9, title = "最后登录ip", cloumn = 15)
    private String lastLoginIp;

    /**
     * 注册来源
     */
    @Schema(description = "注册来源")
    @ExcelResources(order = 10, title = "注册来源", cloumn = 15)
    private String terminal;

    /**
     * 注册渠道
     */
    @Schema(description = "注册渠道")
    @ExcelResources(order = 11, title = "注册渠道", cloumn = 15)
    private String channelName;

}
