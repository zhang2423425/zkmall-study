/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceHttpClientImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.MarketingMediaService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.MarketingMediaServiceImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceApacheHttpImpl;
import com.shop.zkmall.commons.constant.WxCpConstants;
import com.shop.zkmall.commons.utils.third.WxCardUtil;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.config.impl.WxCpTpDefaultConfigImpl;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;
import me.chanjar.weixin.cp.tp.service.impl.WxCpTpServiceApacheHttpClientImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zkmall zkmallvip@163.com
 */
@Configuration
@RequiredArgsConstructor
public class WxServiceConfig {

    private final WxWorkProperties wxWorkProperties;

    private final WxProperties wxProperties;

    @Bean
    public WxMaService getWxMaService() {
        WxMaServiceHttpClientImpl wxMaService = new WxMaServiceHttpClientImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxProperties.getAppId());
        config.setSecret(wxProperties.getSecret());
        wxMaService.setWxMaConfig(config);
        return wxMaService;
    }


    @Bean
    public WxCardUtil getWxCardUtil() {
        return new WxCardUtil(getWxPayService(), getMarketingMediaService());
    }

    @Bean
    public WxCpTpService getWxCpTpService() {
        WxCpTpServiceApacheHttpClientImpl wxCpTpService = new WxCpTpServiceApacheHttpClientImpl();
        WxCpTpDefaultConfigImpl config = new WxCpTpDefaultConfigImpl();
        config.setBaseApiUrl(WxCpConstants.QY_API);
        BeanUtils.copyProperties(wxWorkProperties, config);
        wxCpTpService.setWxCpTpConfigStorage(config);
        return wxCpTpService;
    }

    @Bean
    public WxCpServiceImpl wxCpService() {
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpConfigStorage());
        return wxCpService;
    }

    @Bean
    public WxPayService getWxPayService() {
        WxPayService wxPayService = new WxPayServiceApacheHttpImpl();
        WxPayConfig payConfig = new WxPayConfig();
        BeanUtils.copyProperties(wxProperties, payConfig);
        payConfig.setMchId(wxProperties.getMchid());
        payConfig.setMchKey(wxProperties.getKey());
        payConfig.setKeyPath(wxProperties.getCerturl());

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    @Bean
    public MarketingMediaService getMarketingMediaService() {
        return new MarketingMediaServiceImpl(getWxPayService());
    }

    @Bean
    public WxCpConfigStorage wxCpConfigStorage() {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setBaseApiUrl(WxCpConstants.QY_API);
        BeanUtils.copyProperties(wxWorkProperties, config);
        return config;
    }
}
