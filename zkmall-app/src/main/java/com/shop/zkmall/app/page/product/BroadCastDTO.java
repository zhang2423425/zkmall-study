/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class BroadCastDTO {

    @Schema(description = "用户id")
    private Long buyerUserId;

    @Schema(description = "昵称")
    private String name;

    @Schema(description = "头像")
    private String headImage;

    @Schema(description = "类型 1-给好评 2-正在拼单 3-拼单成功 4-下单")
    private Integer type;

    /**
     * 操作时间
     */
    private String time;

    @Schema(description = "时间 几秒前 几分钟前")
    private String timeStr;

}
