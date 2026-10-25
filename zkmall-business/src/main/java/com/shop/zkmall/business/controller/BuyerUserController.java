/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.buyer.BuyerUser;
import com.shop.zkmall.business.page.buyer.BuyerUserDetail;
import com.shop.zkmall.business.page.buyer.BuyerUserExportDTO;
import com.shop.zkmall.business.param.buyer.*;
import com.shop.zkmall.business.service.buyer.CereBuyerUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("buyer")
@RequiredArgsConstructor
@Tag(name = "客户管理模块", description = "客户管理模块")
public class BuyerUserController {

    private final CereBuyerUserService cereBuyerUserService;

    /**
     * 客户列表
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "客户列表")
    public Result<Page<BuyerUser>> getAll(@RequestBody BuyerUserGetAllParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page<BuyerUser> page = cereBuyerUserService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 商家标签列表
     */
    @PostMapping(value = "getLabels")
    @Operation(summary = "商家标签列表")
    public Result<List<Object>> getLabels() {
        return new Result<>(Collections.emptyList());
    }

    /**
     * 加标签
     */
    @PostMapping(value = "saveLabel")
    @NoRepeatSubmit
    @Operation(summary = "加标签")
    @WebRequestLog
    public Result<String> saveLabel(@RequestBody BuyerUserSaveParam group, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        return new Result<>(user.getUsername(), "加标签", GsonUtil.objectToGson(group));
    }

    /**
     * 添加客户
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加客户")
    @WebRequestLog
    public Result<String> save(@RequestBody UserSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereBuyerUserService.save(param, user);
        return new Result<>(user.getUsername(), "添加客户", GsonUtil.objectToGson(param));
    }

    /**
     * 修改客户
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改客户")
    @WebRequestLog
    public Result<String> update(@RequestBody UserUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereBuyerUserService.update(param, user);
        return new Result<>(user.getUsername(), "修改客户", GsonUtil.objectToGson(param));
    }

    /**
     * 客户详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "客户详情查询")
    public Result<BuyerUserDetail> getById(@RequestBody BuyerUserGetByIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        Long shopId = null;
        if (user != null) {
            shopId = user.getShopId();
        }
        BuyerUserDetail detail = cereBuyerUserService.getById(shopId, param.getBuyerUserId());
        return new Result<>(detail);
    }

    @GetMapping("initBusinessBuyerUser")
    @Operation(summary = "初始化成为商家客户的时间")
    public Result<Boolean> initBusinessBuyerUser() {
        cereBuyerUserService.initBusinessBuyerUser();
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }

    @PostMapping(value = "export")
    @Operation(summary = "客户列表导出")
    public void export(@RequestBody BuyerUserGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        param.setPage(null);
        param.setPageSize(null);
        Page<BuyerUser> page = cereBuyerUserService.getAll(param);
        Map<Long, String> labelNameMap = new HashMap<>();
        List<BuyerUserExportDTO> list = new ArrayList<>();
        for (BuyerUser buyerUser : page.getList()) {
            BuyerUserExportDTO dto = new BuyerUserExportDTO();
            BeanUtils.copyProperties(buyerUser, dto);
            List<Long> ids = buyerUser.getLabelIds();
            if (CollectionUtils.isNotEmpty(ids)) {
                List<String> names = ids.stream().map(labelNameMap::get).filter(StringUtils::isNotBlank).collect(Collectors.toList());
                dto.setLabelName(StringUtils.join(names, ","));
            }
            list.add(dto);
        }
        //定义导出的excel名字
        String excelName = "用户列表";
        ExcelExportUtils.exportExcel(request, response, excelName, list, BuyerUserExportDTO.class);
    }
}
