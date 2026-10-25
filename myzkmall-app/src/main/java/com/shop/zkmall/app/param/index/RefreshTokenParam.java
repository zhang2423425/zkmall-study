/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.index;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 刷新token请求参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "RefreshTokenParam", description = "刷新token请求参数")
public class RefreshTokenParam {

    private String refreshToken;

}
