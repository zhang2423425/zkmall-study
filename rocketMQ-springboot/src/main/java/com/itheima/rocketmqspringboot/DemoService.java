package com.itheima.rocketmqspringboot;

import com.itheima.rocketmqspringboot.domain.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "topic11",consumerGroup = "group1")
public class DemoService implements RocketMQListener<User> {


    @Override
    public void onMessage(User user) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(user);
    }
}
