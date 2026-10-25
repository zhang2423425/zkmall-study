/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.shop.LabelSource;
import com.shop.zkmall.business.param.label.LabelDeleteSourceParam;
import com.shop.zkmall.business.param.label.LabelGetSourceParam;
import com.shop.zkmall.business.param.label.LabelSaveSourceParam;
import com.shop.zkmall.business.service.label.CereLabelSourceService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 素材
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("shop_label")
@RequiredArgsConstructor
@Tag(name = "素材模块", description = "素材模块")
public class ShopLabelController {

    private final CereLabelSourceService cereLabelSourceService;

    /**
     * 上传素材
     */
    @PostMapping(value = "saveSource")
    @NoRepeatSubmit
    @Operation(summary = "上传素材")
    @WebRequestLog
    public Result<String> saveSource(@RequestBody LabelSaveSourceParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereLabelSourceService.saveSource(param, user);
        return new Result<>(user.getUsername(), "上传素材", GsonUtil.objectToGson(param));
    }

    /**
     * 更新素材绑定标签
     */
    @PostMapping(value = "updateSource")
    @NoRepeatSubmit
    @Operation(summary = "更新素材绑定标签")
    @WebRequestLog
    public Result<String> updateSource(@RequestBody LabelSaveSourceParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereLabelSourceService.updateSource(param, user);
        return new Result<>(user.getUsername(), "更新素材绑定标签", GsonUtil.objectToGson(param));
    }

    /**
     * 素材查询
     */
    @PostMapping(value = "getAllByLabel")
    @Operation(summary = "素材查询")
    public Result<List<LabelSource>> getAllByLabel(@RequestBody LabelGetSourceParam param) {
        param.setShopId(ContextUtil.getShopId());
        List<LabelSource> list = cereLabelSourceService.getAllByLabel(param);
        return new Result<>(list);
    }

    /**
     * 素材图片删除
     */
    @PostMapping(value = "deleteSource")
    @NoRepeatSubmit
    @Operation(summary = "素材图片删除")
    @WebRequestLog
    public Result<String> deleteSource(@RequestBody LabelDeleteSourceParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereLabelSourceService.deleteSource(param, user);
        return new Result<>(user.getUsername(), "素材图片删除", GsonUtil.objectToGson(param));
    }
}
