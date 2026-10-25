package com.itheima.rocketmqspringboot;

import com.itheima.rocketmqspringboot.domain.User;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @GetMapping("/send")
    public String send(){
        User user = new User("zhangsan", 23);
        rocketMQTemplate.convertAndSend("topic11",user);

        return "success";
    }

}
