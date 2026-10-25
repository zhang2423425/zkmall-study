/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_log 平台系统日志实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CerePlatformLog implements Serializable {

    /**
     * 日志id
     */
    @Schema(description = "日志id")
    @TableId(type = IdType.AUTO)
    private Long logId;

    /**
     * 关联用户id
     */
    @Schema(description = "关联用户id")
    private Long platformUserId;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String module;

    /**
     * 操作对应工程
     */
    @Schema(description = "操作对应工程")
    private String operation;

    /**
     * 操作功能描述
     */
    @Schema(description = "操作功能描述")
    private String operationDescribtion;

    /**
     * 操作功能对应唯一标识id
     */
    @Schema(description = "操作功能对应唯一标识id")
    private String only;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    private String createTime;

}
