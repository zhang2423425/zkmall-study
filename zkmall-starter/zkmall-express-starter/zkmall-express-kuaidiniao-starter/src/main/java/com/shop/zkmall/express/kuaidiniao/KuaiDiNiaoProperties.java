package com.shop.zkmall.express.kuaidiniao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ConfigurationProperties(prefix = "kdniao")
public class KuaiDiNiaoProperties {

    /**
     * 电商ID
     */
    private String ebusinessId;

    /**
     * 电商加密私钥
     */
    private String appKey;

    /**
     * 获取物流轨迹url
     */
    private String queryUrl;

    /**
     * 获取快递公司编码url
     */
    private String getCodeUrl;

}
