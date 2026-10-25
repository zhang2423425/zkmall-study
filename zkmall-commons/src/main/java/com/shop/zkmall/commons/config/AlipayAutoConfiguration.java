package com.shop.zkmall.commons.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({AlipayConfig.class})
public class AlipayAutoConfiguration {

    @Bean
    public AlipayClient alipayClient(AlipayConfig alipayConfig) {
        log.info("初始化支付宝-{}.", alipayConfig.getAppId());
        return new DefaultAlipayClient(AlipayConfig.URL, alipayConfig.getAppId(),
                alipayConfig.getRsaPrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, alipayConfig.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);
    }

}
