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
 * 删除消息请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "NoticeIdParam", description = "删除消息请求")
public class NoticeIdParam {

    /**
     * 消息id
     */
    @Schema(description = "消息id")
    private Long noticeId;
}
