/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.express;

import lombok.Data;

/**
 * 物流请求接口返回结果实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class HttpResult {
    private int status;
    private String body;
    private String error;

    public HttpResult() {
    }

    public HttpResult(int status, String body, String error) {
        this.status = status;
        this.body = body;
        this.error = error;
    }
}
