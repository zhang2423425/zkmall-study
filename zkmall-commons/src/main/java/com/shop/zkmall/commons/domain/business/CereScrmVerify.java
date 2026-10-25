/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel("scrm同步配置对象")
public class CereScrmVerify implements Serializable {

    @Schema(description = "店铺id")
    @TableId(value = "shop_id", type = IdType.INPUT)
    private Long shopId;

    @Schema(description = "编码后的秘钥")
    private String encodeSecret;

}
