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
 * 公告详情
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "NoticeDetail", description = "公告详情")
public class NoticeDetail {

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
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String noticeContent;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * 消息产生时间
     */
    @Schema(description = "消息产生时间")
    private String createTime;
}
