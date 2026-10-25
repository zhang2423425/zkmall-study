/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_notice 系统消息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereNotice implements Serializable {
    /**
     * 消息id
     */
    @Schema(description = "消息id")
    @TableId(type = IdType.AUTO)
    private Long noticeId;

    /**
     * 消息类型 1-系统消息（系统自动发送） 2-公告（后台录入） 3-站内信
     */
    @Schema(description = "消息类型 1-系统消息（系统自动发送） 2-公告（后台录入） 3-站内信")
    private Integer noticeType;

    /**
     * 跳转类型 1-商品详情 2-订单详情
     */
    private Integer jump;

    /**
     * 接收用户类型 1-全部用户 2-商家 3-普通用户
     */
    private Integer receive;

    /**
     * 是否读取 1-是 0-否
     */
    private Integer ifRead;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 消息标题
     */
    @Schema(description = "消息标题")
    private String noticeTitle;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String noticeContent;

    /**
     * 点击查看详情唯一标识
     */
    private Long only;

    /**
     * 图片
     */
    private String image;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

}
