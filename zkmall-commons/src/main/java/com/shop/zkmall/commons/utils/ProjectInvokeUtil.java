/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.utils;

import com.alibaba.fastjson.JSON;
import com.shop.zkmall.commons.constant.RefreshSkuRealInfoSourceEnum;
import com.shop.zkmall.commons.domain.express.HttpResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
public class ProjectInvokeUtil {

    @Value("${refreshSkuRealInfoUrl:''}")
    private String refreshSkuRealInfoUrl;

    @Value("${verifyCode:''}")
    private String verifyCode;

    public HttpResult postRefreshSkuRealInfo(Long productId,
                                             RefreshSkuRealInfoSourceEnum sourceEnum) {
        return postRefreshSkuRealInfo(productId, null, sourceEnum, null);
    }

    public HttpResult postRefreshSkuRealInfo(Long productId, Long skuId,
                                             RefreshSkuRealInfoSourceEnum sourceEnum,
                                             Integer fictitiousNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("skuId", skuId);
        map.put("fictitiousNumber", fictitiousNumber);
        map.put("sourceEnum", sourceEnum.ordinal());
        map.put("verifyCode", verifyCode);
        if (StringUtils.isBlank(refreshSkuRealInfoUrl)) {
            return new HttpResult(200, "{}", null);
        }
        return HttpUtils.doPostJson(refreshSkuRealInfoUrl, JSON.toJSONString(map));
    }

    public HttpResult postRefreshSkuRealInfoForActivity(Long activityId,
                                                        RefreshSkuRealInfoSourceEnum sourceEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        map.put("sourceEnum", sourceEnum.ordinal());
        map.put("verifyCode", verifyCode);
        if (StringUtils.isBlank(refreshSkuRealInfoUrl)) {
            return new HttpResult(200, "{}", null);
        }
        return HttpUtils.doPostJson(refreshSkuRealInfoUrl, JSON.toJSONString(map));
    }

}
