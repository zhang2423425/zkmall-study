/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.buyer.BuyerUser;
import com.shop.zkmall.admin.page.buyer.BuyerUserDetail;
import com.shop.zkmall.admin.page.buyer.BuyerUserExportDTO;
import com.shop.zkmall.admin.param.buyer.*;
import com.shop.zkmall.admin.param.credit.UpdateCreditParam;
import com.shop.zkmall.admin.param.user.UserSearchParam;
import com.shop.zkmall.admin.service.buyer.CereBuyerUserService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.label.CerePlatformLabel;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
     * 客户管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "客户管理查询")
    public Result<Page<BuyerUser>> getAll(@RequestBody BuyerGetAllParam param) {
        Page<BuyerUser> page = cereBuyerUserService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 客户详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "客户详情查询")
    public Result<BuyerUserDetail> getById(@RequestBody BuyerGetByIdParam param) {
        BuyerUserDetail detail = cereBuyerUserService.getById(param);
        return new Result<>(detail);
    }

    /**
     * 客户绑定标签查询
     */
    @PostMapping(value = "getUserLabels")
    @Operation(summary = "客户绑定标签查询")
    public Result<List<CerePlatformLabel>> getUserLabels(@RequestBody BuyerGetLabelsParam param) {
        List<CerePlatformLabel> list = cereBuyerUserService.getUserLabels(param);
        return new Result<>(list);
    }

    /**
     * 标签查询
     */
    @PostMapping(value = "getLabels")
    @Operation(summary = "标签查询")
    public Result<List<CerePlatformLabel>> getLabels(@RequestBody BuyerGetLabelsParam param) {
        List<CerePlatformLabel> list = cereBuyerUserService.getLabels(param);
        return new Result<>(list);
    }

    /**
     * 贴标签
     */
    @PostMapping(value = "saveUserLabel")
    @NoRepeatSubmit
    @Operation(summary = "贴标签")
    @WebRequestLog
    public Result<String> saveUserLabel(@RequestBody BuyerSaveUserLabelParam param,
                                        HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerUserService.saveUserLabel(param, user);
        return new Result<>(user.getUsername(), "贴标签", GsonUtil.objectToGson(param));
    }

    /**
     * 加入或取消黑名单
     */
    @PostMapping(value = "blacklist")
    @NoRepeatSubmit
    @Operation(summary = "加入和取消黑名单")
    @WebRequestLog
    public Result<String> blacklist(@RequestBody BuyerBlackListParam param,
                                    HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerUserService.blacklist(param, user);
        return new Result<>(user.getUsername(), "加入或取消黑名单", GsonUtil.objectToGson(param));
    }

    /**
     * 更新积分
     */
    @PostMapping(value = "updateCredit")
    @NoRepeatSubmit
    @Operation(summary = "更新积分")
    @WebRequestLog
    public Result<String> updateCredit(@RequestBody UpdateCreditParam param,
                                       HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerUserService.updateCredit(param, user);
        return new Result<>(user.getUsername(), "更改积分", GsonUtil.objectToGson(param));
    }

    /**
     * 搜索用户信息
     */
    @PostMapping(value = "searchUser")
    @NoRepeatSubmit
    @Operation(summary = "搜索用户信息")
    @WebRequestLog
    public Result<Page<CereBuyerUser>> searchUser(@RequestBody UserSearchParam param) {
        Page<CereBuyerUser> page = cereBuyerUserService.searchUser(param);
        return new Result<>(page);
    }

    @PostMapping(value = "export")
    @Operation(summary = "用户列表导出")
    public void export(@RequestBody BuyerGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setPage(null);
        param.setPageSize(null);
        Page<BuyerUser> page = cereBuyerUserService.getAll(param);
        List<BuyerUserExportDTO> list = new ArrayList<>();
        for (BuyerUser buyerUser : page.getList()) {
            BuyerUserExportDTO dto = new BuyerUserExportDTO();
            BeanUtils.copyProperties(buyerUser, dto);
            IntegerEnum terminalEnum = IntegerEnum.TERMINAL_MAP.get(buyerUser.getTerminal());
            if (terminalEnum != null) {
                dto.setTerminal(terminalEnum.getName());
            }
            list.add(dto);
        }
        //定义导出的excel名字
        String excelName = "用户列表";
        ExcelExportUtils.exportExcel(request, response, excelName, list, BuyerUserExportDTO.class);
    }
}
