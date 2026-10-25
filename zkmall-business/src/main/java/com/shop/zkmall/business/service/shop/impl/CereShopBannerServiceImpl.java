/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop.impl;

import com.shop.zkmall.business.dao.shop.CereShopBannerDAO;
import com.shop.zkmall.business.page.shop.ShopBanner;
import com.shop.zkmall.business.param.banner.BannerSaveParam;
import com.shop.zkmall.business.param.banner.BannerUpdateParam;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.shop.CereShopBannerService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.shop.CereShopBanner;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopBannerServiceImpl implements CereShopBannerService {

    private final CereShopBannerDAO cereShopBannerDAO;

    private final CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(BannerSaveParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopBanner cereShopBanner = new CereShopBanner();
        cereShopBanner.setShopId(param.getShopId());
        cereShopBanner.setBannerStyle(param.getBannerStyle());
        cereShopBanner.setBannerImage(EmptyUtils.getImage(param.getImages()));
        cereShopBanner.setBannerUrl(param.getBannerUrl());
        cereShopBanner.setState(IntegerEnum.YES.getCode());
        cereShopBanner.setCreateTime(time);
        cereShopBannerDAO.insert(cereShopBanner);
        //新增日志
        cerePlatformLogService.addLog(user, "banner配置管理", "商户端操作", "添加banner", cereShopBanner.getBannerId(), time);
    }

    @Override
    public void update(BannerUpdateParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopBanner cereShopBanner = new CereShopBanner();
        cereShopBanner.setBannerId(param.getBannerId());
        cereShopBanner.setBannerStyle(param.getBannerStyle());
        cereShopBanner.setBannerImage(EmptyUtils.getImage(param.getImages()));
        cereShopBanner.setBannerUrl(param.getBannerUrl());
        cereShopBanner.setUpdateTime(time);
        cereShopBannerDAO.updateByPrimaryKeySelective(cereShopBanner);
        //新增日志
        cerePlatformLogService.addLog(user, "banner配置管理", "商户端操作", "修改banner", param.getBannerId(), time);
    }

    @Override
    public ShopBanner getById(Long bannerId) {
        ShopBanner banner = cereShopBannerDAO.getById(bannerId);
        if (banner != null) {
            if (!EmptyUtils.isEmpty(banner.getBannerImage())) {
                String[] split = banner.getBannerImage().split(",");
                banner.setImages(Arrays.asList(split));
            }
        }
        return banner;
    }
}
