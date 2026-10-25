/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.stock.impl;

import com.shop.zkmall.app.page.product.ProductStockInfo;
import com.shop.zkmall.app.service.order.CereOrderProductService;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.app.service.stock.CereStockService;
import com.shop.zkmall.commons.cache.constants.CacheKeyConstants;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.exception.CoBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereStockServiceImpl implements CereStockService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedissonClient redissonClient;

    private final CereProductSkuService cereProductSkuService;

    private final CereOrderProductService cereOrderProductService;

    @Override
    public ProductStockInfo getActivityProductStock(Long productId, Integer activityType, Long activityId) {
        String activityProductStockKey = CacheKeyConstants.ACTIVITY_PRODUCT_STOCK_PREFIX + activityType + "_" + productId;
        String activityProductTotalStockKey = CacheKeyConstants.ACTIVITY_PRODUCT_TOTAL_STOCK_PREFIX + activityType + "_" + productId;
        Integer stock = (Integer) redisTemplate.opsForValue().get(activityProductStockKey);
        Integer totalStock = (Integer) redisTemplate.opsForValue().get(activityProductTotalStockKey);
        if (stock != null && totalStock != null) {
            ProductStockInfo productStockInfo = new ProductStockInfo();
            productStockInfo.setProductId(productId);
            productStockInfo.setStockNumber(stock);
            productStockInfo.setTotal(totalStock);
            return productStockInfo;
        } else {
            ProductStockInfo stockInfo = cereProductSkuService.findProductStockInfo(productId);
            if (stockInfo == null) {
                log.error("activity product stock abnormal");
                stockInfo = new ProductStockInfo();
                stockInfo.setProductId(productId);
                stockInfo.setStockNumber(0);
                stockInfo.setTotal(0);
                return stockInfo;
            }
            //保存到缓存中
            redisTemplate.opsForValue().set(activityProductStockKey, stockInfo.getStockNumber());
            redisTemplate.opsForValue().set(activityProductTotalStockKey, stockInfo.getTotal());
            return stockInfo;
        }
    }

    @Override
    public ProductStockInfo getActivitySkuStock(Long skuId, Integer activityType, Long activityId) {
        return getSkuStockInner(skuId, activityType, activityId);
    }

    @Override
    public ProductStockInfo getSkuStockInfo(Long skuId) {
        return getSkuStockInner(skuId, IntegerEnum.NORMAL_PRODUCT.getCode(), 0L);
    }

    private ProductStockInfo getSkuStockInner(Long skuId, Integer activityType, Long activityId) {
        String skuStockKey = CacheKeyConstants.SKU_STOCK_PREFIX + skuId;
        String skuTotalStockKey = CacheKeyConstants.SKU_TOTAL_STOCK_PREFIX + skuId;
        if (!IntegerEnum.NORMAL_PRODUCT.getCode().equals(activityType)) {
            skuStockKey = CacheKeyConstants.ACTIVITY_SKU_STOCK_PREFIX + activityType + "_" + skuId;
            skuTotalStockKey = CacheKeyConstants.ACTIVITY_SKU_TOTAL_STOCK_PREFIX + activityType + "_" + skuId;
        }
        Integer skuStock = (Integer) redisTemplate.opsForValue().get(skuStockKey);
        Integer skuTotalStock = (Integer) redisTemplate.opsForValue().get(skuTotalStockKey);
        if (skuStock != null && skuTotalStock != null) {
            ProductStockInfo productStockInfo = new ProductStockInfo();
            productStockInfo.setStockNumber(skuStock);
            productStockInfo.setTotal(skuTotalStock);
            return productStockInfo;
        } else {
            ProductStockInfo stockInfo = cereProductSkuService.findSkuStockInfo(skuId);
            if (stockInfo == null) {
                log.error("activity sku stock abnormal");
                stockInfo = new ProductStockInfo();
                stockInfo.setStockNumber(0);
                stockInfo.setTotal(0);
                return stockInfo;
            }
            //保存到缓存中
            redisTemplate.opsForValue().set(skuStockKey, stockInfo.getStockNumber());
            redisTemplate.opsForValue().set(skuTotalStockKey, stockInfo.getTotal());
            return stockInfo;
        }
    }

    @Override
    public void updateActivityProductStock(Integer activityType, Long productId, Integer total, Integer stockNumber) {
        String activityProductStockKey = CacheKeyConstants.ACTIVITY_PRODUCT_STOCK_PREFIX + activityType + "_" + productId;
        String activityProductTotalStockKey = CacheKeyConstants.ACTIVITY_PRODUCT_TOTAL_STOCK_PREFIX + activityType + "_" + productId;
        redisTemplate.opsForValue().set(activityProductStockKey, stockNumber);
        redisTemplate.opsForValue().set(activityProductTotalStockKey, total);
    }

    @Override
    public void updateActivitySkuStock(Integer activityType, Long skuId, Integer total, Integer stockNumber) {
        String activitySkuStockKey = CacheKeyConstants.ACTIVITY_SKU_STOCK_PREFIX + activityType + "_" + skuId;
        String activitySkuTotalStockKey = CacheKeyConstants.ACTIVITY_SKU_TOTAL_STOCK_PREFIX + activityType + "_" + skuId;
        redisTemplate.opsForValue().set(activitySkuStockKey, stockNumber);
        redisTemplate.opsForValue().set(activitySkuTotalStockKey, total);
    }

    @Override
    public void updateSkuStock(Long skuId, Integer total, Integer stockNumber) {
        String skuStockKey = CacheKeyConstants.SKU_STOCK_PREFIX + skuId;
        String skuTotalStockKey = CacheKeyConstants.SKU_TOTAL_STOCK_PREFIX + skuId;
        redisTemplate.opsForValue().set(skuStockKey, stockNumber);
        redisTemplate.opsForValue().set(skuTotalStockKey, total);
    }

    @Override
    public void reduceStock(Integer activityType, Long activityId, Long productId, Long skuId, Integer buyNumber, Integer ifOversold) {
        String lockKey = "";
        RLock lock = null;
        boolean oversoldBool = IntegerEnum.YES.getCode().equals(ifOversold);
        try {
            int remainStock = 0;
            lockKey = CacheKeyConstants.PRODUCT_STOCK_LOCK_KEY + productId;
            lock = redissonClient.getLock(lockKey);
            lock.lock();

            // 2、校验常规sku库存
            String skuCacheKey = CacheKeyConstants.SKU_STOCK_PREFIX + skuId;
            Integer skuStock = (Integer) redisTemplate.opsForValue().get(skuCacheKey);
            int skuRemainStock = 0;
            if (skuStock != null) {
                if (skuStock < buyNumber && !oversoldBool) {
                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                }
                skuRemainStock = skuStock - buyNumber;
            } else {
                Integer stockNumber = cereProductSkuService.findStockNumber(skuId);
                if (stockNumber < buyNumber && !oversoldBool) {
                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                }
                skuRemainStock = stockNumber - buyNumber;
            }

            // 4、更新常规sku库存量
            redisTemplate.opsForValue().set(skuCacheKey, skuRemainStock);
            cereProductSkuService.updateStockNumber(skuId, skuRemainStock);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    @Override
    public void rollbackStock(Integer activityType, Long activityId, Long productId, Long skuId, Integer buyNumber) {
        String lockKey = "";
        RLock lock = null;
        try {
            // 1、校验活动库存
            lockKey = CacheKeyConstants.PRODUCT_STOCK_LOCK_KEY + productId;
            lock = redissonClient.getLock(lockKey);
            lock.lock();

            // 2、校验常规sku库存
            String skuCacheKey = CacheKeyConstants.SKU_STOCK_PREFIX + skuId;
            boolean exists = redisTemplate.hasKey(skuCacheKey);
            if (exists) {
                redisTemplate.opsForValue().increment(skuCacheKey, buyNumber);
            }
            cereProductSkuService.rollbackStock(skuId, buyNumber);
        } finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    @Override
    public void rollbackStockByOrderId(Long orderId) {
        //拿到商品库存数据
        List<CereOrderProduct> opList = cereOrderProductService.findByOrderIds(Collections.singletonList(orderId));
        for (CereOrderProduct op : opList) {
            rollbackStock(op.getActivityType(), op.getActivityId(), op.getProductId(), op.getSkuId(), op.getNumber());
        }
    }

    @Override
    public ProductStockInfo getOrQueryShopActivitySkuStock(Integer activityType, Long activityId, Long skuId) {
        ProductStockInfo stockInfo = getActivitySkuStock(skuId, activityType, activityId);
        if (stockInfo != null) {
            return stockInfo;
        }
        return stockInfo;
    }

}
