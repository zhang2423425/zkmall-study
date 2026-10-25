/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.express;

import lombok.Data;

/**
 * 快递鸟单号识别请求返回数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class KuaiDiNiaoCode {

    /**
     * 快递公司名称
     */
    private String ShipperName;

    /**
     * 快递公司编码
     */
    private String ShipperCode;
}
