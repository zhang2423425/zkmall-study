/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceHttpClientImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.shop.zkmall.commons.constant.WxCpConstants;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpUserServiceImpl;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
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

    private final WxAppletProperties wxAppletProperties;

    @Bean
    public WxCpServiceImpl getWxCpServiceImpl() {
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpConfigStorage());
        return wxCpService;
    }

    @Bean
    public WxCpUserService getWxCpUserService() {
        return new WxCpUserServiceImpl(getWxCpServiceImpl());
    }

    @Bean
    public WxMaService getWxMaService() {
        WxMaServiceHttpClientImpl wxMaService = new WxMaServiceHttpClientImpl();
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(wxAppletProperties.getAppid());
        wxMaConfig.setSecret(wxAppletProperties.getSecret());
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }

    @Bean
    public WxCpConfigStorage wxCpConfigStorage() {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setBaseApiUrl(WxCpConstants.QY_API);
        BeanUtils.copyProperties(wxWorkProperties, config);
        return config;
    }

}
