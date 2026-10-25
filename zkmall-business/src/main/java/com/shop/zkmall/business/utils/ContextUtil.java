/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.utils;

import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.exception.CoBusinessException;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
public class ContextUtil {

    static ThreadLocal<Long> shopTL = new ThreadLocal<>();

    public static void setShopId(Long shopId) {
        shopTL.set(shopId);
    }

    public static void clearShopId() {
        shopTL.remove();
    }

    public static Long getShopId() {
        Long shopId = shopTL.get();
        if (shopId == null) {
            throw new CoBusinessException(CoReturnFormat.USER_NOT_LOGIN);
        }
        return shopTL.get();
    }

    public static void interceptDelete(CerePlatformBusiness user) {
        if (user != null && user.getBusinessUserId() == 125) {
            throw new CoBusinessException(CoReturnFormat.DEMON_ACCOUNT_NOT_PERMITTED);
        }
    }
}
