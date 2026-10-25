package com.shop.zkmall.app.redis.component;

import com.esotericsoftware.minlog.Log;
import com.shop.zkmall.app.redis.config.RedisDelayedQueueListener;
import com.shop.zkmall.app.redis.listener.DelayQueueConsumeListener;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
*
* 消费者初始化
* */
@Component
@RequiredArgsConstructor
public class RedisDelayedQueueInit implements ApplicationContextAware {

    private final RedissonClient redissonClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayedQueueListener> delayedQueueListenerMap =
                applicationContext.getBeansOfType(RedisDelayedQueueListener.class);
        delayedQueueListenerMap.entrySet().forEach(entry -> {
            String listenerName = entry.getKey();
            RedisDelayedQueueListener listener = entry.getValue();
            startThread(listenerName, listener);
        });
    }


    private <T> void startThread(String queueName, RedisDelayedQueueListener listener) {
        RBlockingQueue<T> blockingQueue = redissonClient.getBlockingQueue(DelayQueueConsumeListener.class.getName());

        new Thread(() -> {
            Log.info("----------startThread");
            Log.info("----------监听队列名: " + DelayQueueConsumeListener.class.getName());


            while (true) {

                try {
                    T t = blockingQueue.take();
                    Log.info("----------T t = blockingQueue.take();"+t);
                    new Thread(() -> {
                        listener.invoke(t);
                    }, DelayQueueConsumeListener.class.getName()).start();

                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }).start();

    }
}
