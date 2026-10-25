/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.permission.Permission;
import com.shop.zkmall.admin.param.permission.*;
import com.shop.zkmall.admin.service.permission.CerePlatformPermissionService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单权限
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("permission")
@RequiredArgsConstructor
@Tag(name = "菜单权限模块", description = "菜单权限模块")
public class PermissionController {

    private final CerePlatformPermissionService cerePlatformPermissionService;

    /**
     * 添加菜单权限
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加菜单权限")
    @WebRequestLog
    public Result<String> insert(@RequestBody @Validated PermissionSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPermissionService.save(param, user);
        return new Result<>(user.getUsername(), "添加菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 修改菜单权限
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改菜单权限")
    @WebRequestLog
    public Result<String> update(@RequestBody PermissionUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPermissionService.update(param, user);
        return new Result<>(user.getUsername(), "修改菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 删除菜单权限
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除菜单权限")
    @WebRequestLog
    public Result<String> delete(@RequestBody PermissionDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformPermissionService.delete(param, user);
        return new Result<>(user.getUsername(), "删除菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 菜单权限编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "菜单权限编辑查询")
    public Result<CerePlatformPermission> getById(@RequestBody PermissionGetByIdParam param) {
        CerePlatformPermission cerePlatformPermission = cerePlatformPermissionService.getById(param.getPermissionId());
        return new Result<>(cerePlatformPermission);
    }

    /**
     * 查询当前最大排序值
     */
    @PostMapping(value = "getMaxSort")
    @Operation(summary = "查询当前最大排序值")
    public Result<Integer> getMaxSort() {
        Integer sort = cerePlatformPermissionService.getMaxSort();
        return new Result<>(sort);
    }

    /**
     * 菜单权限管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "菜单权限管理查询")
    public Result<Page<Permission>> getAll(@RequestBody PermissionGetAllParam param) {
        Page<Permission> page = cerePlatformPermissionService.getAll(param);
        return new Result<>(page);
    }

}
