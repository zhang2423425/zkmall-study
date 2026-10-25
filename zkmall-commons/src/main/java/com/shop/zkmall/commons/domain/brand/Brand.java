/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.brand;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 品牌表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 * @since 2022-08-26
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@TableName("cere_brand")
@ApiModel(value = "Brand", description = "品牌表")
@AllArgsConstructor
public class Brand implements Serializable {

    /**
     * 品牌id
     */
    @Schema(description = "品牌id")
    @NotNull(message = "品牌id不能为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    @NotEmpty(message = "品牌名称不能为空")
    @Length(max = 64, message = "品牌名称长度不能超过64")
    @TableField(value = "brand_name", condition = LIKE)
    private String brandName;


    /**
     * 品牌图标
     */
    @Schema(description = "品牌图标")
    @NotEmpty(message = "品牌图标不能为空")
    @Length(max = 1024, message = "品牌图标长度不能超过1024")
    @TableField(value = "brand_logo", condition = LIKE)
    private String brandLogo;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @NotEmpty(message = "创建时间不能为空")
    @Length(max = 20, message = "创建时间长度不能超过20")
    @TableField(value = "create_time", condition = LIKE)
    private String createTime;


    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @NotEmpty(message = "更新时间不能为空")
    @Length(max = 20, message = "更新时间长度不能超过20")
    @TableField(value = "update_time", condition = LIKE)
    private String updateTime;

}
