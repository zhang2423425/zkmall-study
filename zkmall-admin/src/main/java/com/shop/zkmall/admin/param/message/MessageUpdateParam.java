/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更细短信配置请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "MessageUpdateParam", description = "更细短信配置请求")
public class MessageUpdateParam {

    /**
     * 短信id
     */
    @Schema(description = "短信id")
    private Long messageId;

    /**
     * 第三方短信名称
     */
    @Schema(description = "第三方短信名称")
    private String messageProject;

    /**
     * 短信模板名称
     */
    @Schema(description = "短信模板名称")
    private String messageTemplate;

    /**
     * 模板编码
     */
    @Schema(description = "模板编码")
    private String templateCode;

    /**
     * 模板签名
     */
    @Schema(description = "模板签名")
    private String templateSign;

    /**
     * 模板描述
     */
    @Schema(description = "模板描述")
    private String templateDescribe;
}
