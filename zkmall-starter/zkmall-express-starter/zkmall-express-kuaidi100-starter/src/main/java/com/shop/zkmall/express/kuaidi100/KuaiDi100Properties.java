package com.shop.zkmall.express.kuaidi100;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "kd100")
public class KuaiDi100Properties {

    /**
     * 授权码
     */
    private String key;

    /**
     * 公司编号
     */
    private String customer;

    /**
     * 获取物流轨迹url
     */
    private String queryUrl;

}
