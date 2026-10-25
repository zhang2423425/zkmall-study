/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.miaoxin;

import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.sms.miaoxin.sdk.MiaoxinSmsClient;
import com.shop.zkmall.sms.model.MessageResult;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
public class MiaoXinSmsStrategy implements SmsStrategy {

    private final MiaoXinProperties miaoXinProperties;

    public MiaoXinSmsStrategy(MiaoXinProperties miaoXinProperties) {
        this.miaoXinProperties = miaoXinProperties;
    }

    @Override
    public String sendNotice(String phone, Map<String, String> map) throws Exception {
        MiaoxinSmsClient client = new MiaoxinSmsClient();
        String format = MessageFormat.format(miaoXinProperties.getTemplate(), map.values().toArray(new String[0]));
        String content = "【" + miaoXinProperties.getSignName() + "】" + format;
        client.setAccount(miaoXinProperties.getAccount());
        client.setPassword(miaoXinProperties.getSecret());
        String result = client.send(phone, content, "ref", "103");
        log.info("result {}", result);
        //返回结果转对象
        MessageResult messageResult = JSONObject.parseObject(result, MessageResult.class);
        return messageResult.getCode();
    }

    @Override
    public String sendContent(String phone, String content) {
        return null;
    }

}
