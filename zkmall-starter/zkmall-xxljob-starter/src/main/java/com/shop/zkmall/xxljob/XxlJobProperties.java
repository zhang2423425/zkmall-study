/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.xxljob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {
    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 调度中心部署根地址
     */
    private String adminAddresses;

    /**
     * 调度中心通讯TOKEN
     */
    private String accessToken;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题
     */
    private String address;

    /**
     * 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯使用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
     */
    private String ip;

    /**
     * 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
     */
    private int port = 9999;

    /**
     * 日志路径
     */
    private String logPath;

    /**
     * 调度中心日志表数据保存天数
     */
    private Integer logRetentionDays = 1;
}

