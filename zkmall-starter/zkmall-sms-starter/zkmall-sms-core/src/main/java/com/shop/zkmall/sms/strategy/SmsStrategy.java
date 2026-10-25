/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.strategy;

import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface SmsStrategy {

    String sendNotice(String phone, Map<String, String> map) throws Exception;

    /**
     * 发送内容
     */
    String sendContent(String phone, String content);

}
