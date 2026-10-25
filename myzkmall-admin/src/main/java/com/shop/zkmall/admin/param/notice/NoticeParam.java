/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 消息推送请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "NoticeParam", description = "消息推送请求")
public class NoticeParam {

    /**
     * 消息标题
     */
    @Schema(description = "消息标题")
    private String noticeTitle;

    /**
     * 消息类型 1-系统消息（系统自动发送） 2-公告（后台录入）3-站内信
     */
    @Schema(description = "消息类型 1-系统消息（系统自动发送） 2-公告（后台录入）3-站内信")
    private Integer noticeType;

    /**
     * 接收用户类型 1-全部用户 2-商家 4-普通用户
     */
    @Schema(description = "接收用户类型 1-全部用户 2-商家 3-普通用户")
    private Integer receive;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private String noticeContent;
}
