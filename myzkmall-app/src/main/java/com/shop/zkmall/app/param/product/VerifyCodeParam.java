/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.product;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class VerifyCodeParam implements Serializable {

    /**
     * 刷新sku实时信息的校验码
     */
    private String verifyCode;

}
