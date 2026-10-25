/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils.third;

import com.github.binarywang.wxpay.service.MarketingMediaService;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Data
@AllArgsConstructor
public class WxCardUtil {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /* 优惠券顶部图标 */
    private static final String COUPON_LOGO_URL = "https://zk-cereshop.oss-cn-shenzhen.aliyuncs.com/test/2021-12-11/34a18c9c0efc4a8a80a726ba6b6c7765coupon.jpeg";

    private WxPayService wxPayService;

    private MarketingMediaService marketingMediaService;

}
