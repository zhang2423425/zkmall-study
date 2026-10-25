/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 添加短信配置请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "MessageSaveParam", description = "添加短信配置请求")
public class MessageSaveParam {

    /**
     * 第三方短信名称
     */
    @Schema(description = "第三方短信名称")
    @NotBlank(message = "第三方短信名称不能为空")
    private String messageProject;

    /**
     * 短信模板名称
     */
    @Schema(description = "短信模板名称")
    @NotBlank(message = "短信模板名称不能为空")
    private String messageTemplate;

    /**
     * 模板编码
     */
    @Schema(description = "模板编码")
    @NotBlank(message = "模板编码不能为空")
    private String templateCode;

    /**
     * 模板签名
     */
    @Schema(description = "模板签名")
    @NotBlank(message = "模板签名不能为空")
    private String templateSign;

    /**
     * 模板描述
     */
    @Schema(description = "模板描述")
    @NotBlank(message = "模板描述不能为空")
    private String templateDescribe;
}
