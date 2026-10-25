/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.brand;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class BrandParam {

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

}
