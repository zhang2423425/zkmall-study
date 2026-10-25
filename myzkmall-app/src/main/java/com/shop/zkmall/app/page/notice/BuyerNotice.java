/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 消息数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "BuyerNotice", description = "消息数据")
public class BuyerNotice {

    /**
     * 消息id
     */
    @Schema(description = "消息id")
    private Long noticeId;

    /**
     * 消息标题
     */
    @Schema(description = "消息标题")
    private String noticeTitle;

    /**
     * 消息类型 消息类型 1-系统消息（系统自动发送） 2-公告（后台录入） 3-站内信
     */
    @Schema(description = "消息类型 1-系统消息（系统自动发送） 2-公告（后台录入） 3-站内信")
    private Integer noticeType;

    /**
     * 跳转类型 1-商品详情 2-订单详情
     */
    @Schema(description = "跳转类型 1-商品详情 2-订单详情")
    private Integer jump;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String noticeContent;

    /**
     * 消息产生时间
     */
    @Schema(description = "消息产生时间")
    private String createTime;

    /**
     * 点击查看详情唯一标识
     */
    @Schema(description = "点击查看详情唯一标识")
    private Long only;

    /**
     * 是否读取 1-是 0-否
     */
    @Schema(description = "是否已读")
    private Integer ifRead;
}
