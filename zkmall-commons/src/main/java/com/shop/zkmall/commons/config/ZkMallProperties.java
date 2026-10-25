package com.shop.zkmall.commons.config;

import com.shop.zkmall.commons.enums.LogisticsTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@Component
@Validated
@ConfigurationProperties(prefix = "zkmall")
public class ZkMallProperties {

    @NotBlank(message = "网站域名不能为空")
    private String domain;

    /**
     * 物流获取接口(默认快递100)
     */
    private LogisticsTypeEnum logisticsType = LogisticsTypeEnum.KUAIDI100;

}
