/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.message;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取短信配置详情请求
 *
 * @author zkmall zkmallvip@163.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "MessageGetAllParam", description = "获取短信配置详情请求")
public class MessageGetAllParam extends PageParam {

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
}
