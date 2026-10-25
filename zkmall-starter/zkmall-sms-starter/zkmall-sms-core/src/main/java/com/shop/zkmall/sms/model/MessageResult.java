/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.sms.model;

import lombok.Data;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class MessageResult {

    /**
     * 短信发送返回编码
     */
    private String code;

    /**
     * 短信发送返回提示信息
     */
    private String msg;

    /**
     * 发送短信请求数
     */
    private Integer total;

    /**
     * 返回结果数组
     */
    private List<Message> result;
}
