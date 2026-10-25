/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.permission.RolePermission;
import com.shop.zkmall.admin.param.role.*;
import com.shop.zkmall.admin.service.permission.CerePlatformPermissionService;
import com.shop.zkmall.admin.service.role.CerePlatformRoleService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.role.CerePlatformRole;
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
 * 角色
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("role")
@RequiredArgsConstructor
@Tag(name = "角色模块", description = "角色模块")
public class RoleController {

    private final CerePlatformRoleService cerePlatformRoleService;

    private final CerePlatformPermissionService cerePlatformPermissionService;

    /**
     * 添加角色
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加角色")
    @WebRequestLog
    public Result<String> insert(@RequestBody @Validated RoleSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.save(param, user);
        return new Result<>(user.getUsername(), "添加角色", GsonUtil.objectToGson(param));
    }

    /**
     * 修改角色
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改角色")
    @WebRequestLog
    public Result<String> update(@RequestBody RoleUpdateParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.update(param, user);
        return new Result<>(user.getUsername(), "修改角色", GsonUtil.objectToGson(param));
    }

    /**
     * 删除角色
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除角色")
    @WebRequestLog
    public Result<String> delete(@RequestBody RoleDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.delete(param, user);
        return new Result<>(user.getUsername(), "删除角色", GsonUtil.objectToGson(param));
    }

    /**
     * 角色编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "角色编辑查询")
    public Result<CerePlatformRole> getById(@RequestBody RoleGetByIdParam role) {
        CerePlatformRole cerePlatformRole = cerePlatformRoleService.getById(role.getRoleId());
        return new Result<>(cerePlatformRole);
    }

    /**
     * 角色管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "角色管理查询")
    public Result<Page<CerePlatformRole>> getAll(@RequestBody RoleGetAllParam param) {
        Page<CerePlatformRole> page = cerePlatformRoleService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 菜单分配
     */
    @PostMapping(value = "distribution")
    @NoRepeatSubmit
    @Operation(summary = "菜单分配")
    @WebRequestLog
    public Result<String> distribution(@RequestBody RoleDistributionParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformRoleService.distribution(param, user);
        return new Result<>(user.getUsername(), "菜单分配", GsonUtil.objectToGson(param));
    }

    /**
     * 菜单分配查询
     */
    @PostMapping(value = "getRolePermission")
    @NoRepeatSubmit
    @Operation(summary = "菜单分配查询")
    public Result<RolePermission> getRolePermission(@RequestBody RoleDistributionParam param) {
        RolePermission rolePermission = cerePlatformPermissionService.getRolePermission(param);
        return new Result<>(rolePermission);
    }
}
