package com.shop.zkmall.app.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.shop.zkmall.app.utils.RedisConstants.CACHE_NULL_TTL;
import static com.shop.zkmall.app.utils.RedisConstants.LOCK_SHOP_KEY;


@Slf4j
@Component
public class CacheClient {

    private final StringRedisTemplate stringRedisTemplate;

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    public CacheClient(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private void set(String key, Object value, Long time, TimeUnit unit) {
        if (value instanceof Number || value instanceof CharSequence || value instanceof Boolean) {
            // 基本类型/包装类型：直接字符串，别用 JSONUtil
            stringRedisTemplate.opsForValue().set(key, String.valueOf(value), time, unit);
        } else {
            // 对象类型：正常 JSON
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
        }
    }


    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 写入Redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

  //缓存穿透
    public <R,ID> R queryWithPassThrough(
            String keyPrefix,ID id,Class<R> type,Function<ID,R> dbFallback,Long time,TimeUnit unit){

        String key=keyPrefix+id;
        String json = stringRedisTemplate.opsForValue().get(key);
        if(StrUtil.isNotBlank(json)){
            //非null和"",返回正常
            return JSONUtil.toBean(json,type);
        }
        if (json != null) {
            //为空串
            return null;
        }
        //json==null
        R r=dbFallback.apply(id);
        if (r == null) {
            stringRedisTemplate.opsForValue().set(key,"",CACHE_NULL_TTL,TimeUnit.MINUTES);
            return null;
        }
        set(key,JSONUtil.toJsonStr(r),time,unit);

        return r;
    }

    public <R, ID> R queryWithLogicalExpire(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1.从redis查询商铺缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isBlank(json)) {
            // 3.存在，直接返回
            return null;
        }
        // 4.命中，需要先把json反序列化为对象
        RedisData redisData = JSONUtil.toBean(json, RedisData.class);
        R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 5.判断是否过期
        if(expireTime.isAfter(LocalDateTime.now())) {
            // 5.1.未过期，直接返回店铺信息
            return r;
        }
        // 5.2.已过期，需要缓存重建
        // 6.缓存重建
        // 6.1.获取互斥锁
        String lockKey = LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(lockKey);
        // 6.2.判断是否获取锁成功
        if (isLock){
            // 6.3.成功，开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查询数据库
                    R newR = dbFallback.apply(id);
                    // 重建缓存
                    this.setWithLogicalExpire(key, newR, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    // 释放锁
                    unlock(lockKey);
                }
            });
        }
        // 6.4.返回过期的商铺信息
        return r;
    }

    //击穿
    public <R ,ID> R queryWithMutex(
            String keyPrefix,ID id,Class<R> type,Function<ID,R> dbFallback,Long time,TimeUnit unit){
        String key=keyPrefix+id;
        String json = stringRedisTemplate.opsForValue().get(key);
        if(StrUtil.isNotBlank(json)){
            //正常返回
            return JSONUtil.toBean(json,type,true);
        }
        if (json != null) {
            //为空串
            return null;
        }
        //json==null
        String lockKey=LOCK_SHOP_KEY+id;
        R r=null;
        try {
            boolean isLock = tryLock(lockKey);
            if (!isLock) {
                //竞争失败,自旋
                Thread.sleep(200);
                return queryWithMutex(keyPrefix,id,type,dbFallback,time,unit);
            }
            //上锁了
            r=dbFallback.apply(id);
            if (r == null) {
                stringRedisTemplate.opsForValue().set(key,"",CACHE_NULL_TTL,TimeUnit.MINUTES);
                return null;
            }
            set(key,r,time,unit);


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            unlock(lockKey);
        }
        return r;
    }


    public <ID,R> List<String> queryWithMutexForHash(
            String key, Collection<ID> collection, Function<Collection<ID>,List<R>> dbFallback, Long time, TimeUnit unit){
        //操作数据库时使用
        List<ID> idList=new ArrayList<>(collection);
        //操作redis时使用
        List<String> fieldList = collection.stream().map(Object::toString).collect(Collectors.toList());
        //List<Object> list = stringRedisTemplate.opsForHash().multiGet(key, fieldList);
        //规定泛型
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        List<String> redisValueList =  hashOperations.multiGet(key, fieldList);

        List<ID> absentValueIDs=new ArrayList<>();
        for (int i = 0; i < redisValueList.size(); i++) {
            if(redisValueList.get(i)==null){
                absentValueIDs.add(idList.get(i));
            }
            if("".equals(redisValueList.get(i))){
                throw new CoBusinessException(CoReturnFormat.HAVE_INVALID_PRODUCT);
            }
        }

        //没有缺失，直接返回
        if(absentValueIDs.isEmpty()){
            return redisValueList;
        }

        //有缺失，加锁，查数据库
        String lockKey = LOCK_SHOP_KEY + key;
        try {

            boolean isLocked = tryLock(lockKey);
            if (!isLocked) {
                Thread.sleep(200);
                return queryWithMutexForHash(key,collection,dbFallback,time,unit);
            }
            boolean isValidFlag=true;
            List<R> dbList=dbFallback.apply(absentValueIDs);
            //将查到的null缓存空值,正常值也缓存
            Map<String,String> nullOrNormalMap= new HashMap<>();
            for (int i = 0; i < dbList.size(); i++) {
                if (dbList.get(i) == null) {
                    nullOrNormalMap.put(absentValueIDs.get(i).toString(),"");
                    log.info("------------put-------null-----");
                    isValidFlag=false;
                }else {
                    nullOrNormalMap.put(absentValueIDs.get(i).toString(),String.valueOf(dbList.get(i)));
                }
            }
            if(!EmptyUtils.isEmpty(nullOrNormalMap)){
                hashOperations.putAll(key,nullOrNormalMap);
                //整体过期
                stringRedisTemplate.expire(key,time,unit);

            }
            if (!isValidFlag) {
                throw new CoBusinessException(CoReturnFormat.HAVE_INVALID_PRODUCT);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            unlock(lockKey);
        }
        return hashOperations.multiGet(key, fieldList);
    }


    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
