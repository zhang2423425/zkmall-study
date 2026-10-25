/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.cache.constants;

/**
 * @author zkmall zkmallvip@163.com
 */
public class CacheKeyConstants {

    /**
     * sku缓存的key前缀
     */
    public static final String SKU_CACHE_PREFIX = "sku_cache_";

    /**
     * sku总库存的key前缀
     */
    public static final String SKU_STOCK_PREFIX = "sku_stock_";

    /**
     * sku库存的key前缀
     */
    public static final String SKU_TOTAL_STOCK_PREFIX = "sku_total_stock_";

    /**
     * 活动商品级库存 平台活动是商品级别的，所以存储的是商品级别的库存
     */
    public static final String ACTIVITY_PRODUCT_STOCK_PREFIX = "activity_product_stock_";

    /**
     * 活动商品级库存 平台活动是商品级别的，所以存储的是商品级别的总库存
     */
    public static final String ACTIVITY_PRODUCT_TOTAL_STOCK_PREFIX = "activity_product_total_stock_";

    /**
     * 活动sku级库存，商家活动是针对sku级别的，所以存储的是sku级别的库存
     */
    public static final String ACTIVITY_SKU_STOCK_PREFIX = "activity_sku_stock_";

    /**
     * 活动sku级库存，商家活动是针对sku级别的，所以存储的是sku级别的总库存
     */
    public static final String ACTIVITY_SKU_TOTAL_STOCK_PREFIX = "activity_sku_total_stock_";

    /**
     * 查询商品库存加锁
     */
    public static final String PRODUCT_STOCK_LOCK_KEY = "product_stock_lock_";

    /**
     * 查询sku库存加锁
     */
    public static final String SKU_STOCK_LOCK_KEY = "sku_stock_lock_";

    /**
     * 查询或操作积分加锁
     */
    public static final String CREDIT_LOCK = "credit_lock_";

    /**
     * 验证码缓存前缀
     */
    public static final String CAPTCHA_PREFIX = "captcha:app:";

    /**
     * 发送短信前缀
     */
    public static final String SEND_SMS_CODE_PREFIX = "send:sms:code:";
}
