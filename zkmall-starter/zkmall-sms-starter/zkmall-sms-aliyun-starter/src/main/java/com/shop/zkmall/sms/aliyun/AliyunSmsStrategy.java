/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
public class AliyunSmsStrategy implements SmsStrategy {

    private final AliyunSmsProperties aliyunSmsProperties;

    public AliyunSmsStrategy(AliyunSmsProperties aliyunSmsProperties) {
        this.aliyunSmsProperties = aliyunSmsProperties;
    }

    @Override
    public String sendNotice(String phone, Map<String, String> map) throws Exception {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");


        Client client = createClient();
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(aliyunSmsProperties.getSignName())
                .setTemplateCode(aliyunSmsProperties.getTemplate())
                .setTemplateParam(JSON.toJSONString(map));
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, new com.aliyun.teautil.models.RuntimeOptions());
        } catch (Exception error) {
            log.error("发送错误:", error);
            throw new RuntimeException("发送短信异常:", error);
        }

        return null;
    }

    @Override
    public String sendContent(String phone, String content) {
        return null;
    }

    public Client createClient() throws Exception {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(aliyunSmsProperties.getKeyId())
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(aliyunSmsProperties.getKeySecret());
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = aliyunSmsProperties.getDomain();
        return new Client(config);
    }

}
