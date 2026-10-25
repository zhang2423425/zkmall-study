/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.oss;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 上传文件返回地址
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@AllArgsConstructor
public class Url {

    /**
     * 文件地址
     */
    @Schema(description = "文件地址")
    private String url;
}
