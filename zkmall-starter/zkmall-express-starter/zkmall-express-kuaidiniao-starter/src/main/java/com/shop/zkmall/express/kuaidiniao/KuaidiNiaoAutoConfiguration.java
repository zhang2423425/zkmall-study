package com.shop.zkmall.express.kuaidiniao;

import com.shop.zkmall.express.IExpressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "zkmall", name = "logistics-type", havingValue = "kuaidiniao")
@EnableConfigurationProperties(KuaiDiNiaoProperties.class)
public class KuaidiNiaoAutoConfiguration {

    private final KuaiDiNiaoProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public IExpressService expressService() {
        log.info("初始化快递鸟物流查询.");
        return new KuaiDiNiaoServiceImpl(properties);
    }

}
