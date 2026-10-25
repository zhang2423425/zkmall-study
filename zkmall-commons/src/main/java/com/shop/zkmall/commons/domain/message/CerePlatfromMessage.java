/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platfrom_message 短信账号配置信息实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CerePlatfromMessage", description = "短信账号配置信息实体")
public class CerePlatfromMessage implements Serializable {
    /**
     * 短信id
     */
    @Schema(description = "短信id")
    @TableId(type = IdType.AUTO)
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

    /**
     * 是否启用 1-是 0-否
     */
    @Schema(description = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;

}
