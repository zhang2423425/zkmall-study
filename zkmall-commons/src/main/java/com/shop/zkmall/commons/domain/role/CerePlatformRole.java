/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_role  平台角色实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CerePlatformRole", description = "平台角色实体类")
public class CerePlatformRole implements Serializable {
    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @Schema(description = "角色描述")
    private String roleDescribe;

    /**
     * 角色所属项目 1-平台端 2-商家端
     */
    @Schema(description = "角色所属项目 1-平台端 2-商家端")
    private Long project;

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
