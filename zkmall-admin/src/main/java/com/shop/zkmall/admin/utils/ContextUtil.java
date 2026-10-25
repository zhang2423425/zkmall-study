/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.utils;

import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import org.springframework.stereotype.Component;

/**
 * @author zkmall zkmallvip@163.com
 */
@Component
public class ContextUtil {

    static ThreadLocal<Boolean> adminTL = new ThreadLocal<>();

    static ThreadLocal<String> uriTL = new ThreadLocal<>();

    public static void setAdmin() {
        adminTL.set(true);
    }

    public static void clearAdmin() {
        adminTL.set(false);
    }

    public static boolean getAdmin() {
        //return false;
        return adminTL.get() != null && adminTL.get();
    }

    public static void setUri(String uri) {
        uriTL.set(uri);
    }

    public static void clearUri() {
        uriTL.remove();
    }

    public static String getUri() {
        return uriTL.get();
    }

    public static void interceptDelete() {
        throw new CoBusinessException(CoReturnFormat.DEMON_ACCOUNT_NOT_PERMITTED);
    }

}
