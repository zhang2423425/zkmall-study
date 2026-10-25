/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_dict 数据字典实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CerePlatformDict", description = "数据字典实体类")
public class CerePlatformDict implements Serializable {

    /**
     * 字典id
     */
    @Schema(description = "字典id")
    @TableId(type = IdType.AUTO)
    private Long dictId;

    /**
     * 父节点id
     */
    @Schema(description = "父节点id")
    private Long dictPid;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述")
    private String dictDescribe;

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
