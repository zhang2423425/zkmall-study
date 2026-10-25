package com.shop.zkmall.app.jobs;


import cn.hutool.core.util.ArrayUtil;
import com.shop.zkmall.app.utils.RedisConstants;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class MyXxlJobHandler {

    private final StringRedisTemplate stringRedisTemplate;

    public static Set<Long> top1000HotSkuIdsSet;

    @XxlJob("refreshHotSkuId")
    public void refreshHotSkuId(){
        log.info("---------refreshHotSkuId,begin---------");
        String key = RedisConstants.HOT_SKU + LocalDate.now();
        Set<String> top1000HotSkuIds = stringRedisTemplate.opsForZSet().
                reverseRange(key, 0, 999);
        if (top1000HotSkuIds!=null){
            stringRedisTemplate.delete(RedisConstants.TOP1000_SKUIDS);
            stringRedisTemplate.opsForSet().add(RedisConstants.TOP1000_SKUIDS, top1000HotSkuIds.toArray(new String[0]));
            getTop1000();
        }


        //重新统计
//        stringRedisTemplate.delete(key);
        log.info("---------refreshHotSkuId,top1000HotSkuIdsSet is refreshed,end---------");
    }
    @PostConstruct
    public void getTop1000(){
        Set<String> top1000Skus = stringRedisTemplate.opsForSet().members(RedisConstants.TOP1000_SKUIDS);
        if (top1000Skus!=null){
            top1000HotSkuIdsSet=top1000Skus.stream().map(Long::valueOf).collect(Collectors.toSet());
        }
    }

}
