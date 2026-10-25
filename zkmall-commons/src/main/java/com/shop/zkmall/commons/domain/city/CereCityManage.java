/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.city;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_city_manage 城市管理实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereCityManage", description = "城市管理实体类")
public class CereCityManage implements Serializable {
    /**
     * 城市id
     */
    @Schema(description = "城市id")
    @TableId(type = IdType.AUTO)
    private Long cityId;

    /**
     * 上级节点id
     */
    @Schema(description = "上级节点id")
    private Long cityPid;

    /**
     * 城市名称
     */
    @Schema(description = "城市名称")
    private String cityName;

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
