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
import com.shop.zkmall.admin.service.permission.CereBusinessPermissionService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * 商家菜单权限
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("businessPermission")
@RequiredArgsConstructor
@Tag(name = "菜单权限模块", description = "商家菜单权限模块")
public class BusinessPermissionController {

    private final CereBusinessPermissionService cereBusinessPermissionService;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 同步后台菜单时的key
     */
    private static final String SYNC_MENU_KEY = "sync_menu_ing";

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
        cereBusinessPermissionService.save(param, user);
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
        cereBusinessPermissionService.update(param, user);
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
        cereBusinessPermissionService.delete(param, user);
        return new Result<>(user.getUsername(), "删除菜单权限", GsonUtil.objectToGson(param));
    }

    /**
     * 菜单权限编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "菜单权限编辑查询")
    public Result<CerePlatformPermission> getById(@RequestBody PermissionGetByIdParam param) {
        CerePlatformPermission cerePlatformPermission = cereBusinessPermissionService.getById(param.getPermissionId());
        return new Result<>(cerePlatformPermission);
    }

    /**
     * 查询当前最大排序值
     */
    @PostMapping(value = "getMaxSort")
    @Operation(summary = "查询当前最大排序值")
    public Result<Integer> getMaxSort() {
        Integer sort = cereBusinessPermissionService.getMaxSort();
        return new Result<>(sort);
    }

    /**
     * 菜单权限管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "菜单权限管理查询")
    public Result<Page<Permission>> getAll(@RequestBody PermissionGetAllParam param) {
        Page<Permission> page = cereBusinessPermissionService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 菜单权限管理查询
     */
    @PostMapping(value = "syncMenu")
    @Operation(summary = "同步菜单")
    public Result<Boolean> syncMenu(@RequestBody SyncMenuParam param) {
        if (param.isSyncAll()) {
            String syncIng = (String) redisTemplate.opsForValue().get(SYNC_MENU_KEY);
            if (StringUtils.isNotBlank(syncIng)) {
                throw new CoBusinessException(CoReturnFormat.SYNC_MENU_ING);
            } else {
                redisTemplate.opsForValue().set(SYNC_MENU_KEY, "on", Duration.ofMinutes(5));
            }
        }
        cereBusinessPermissionService.syncMenu(param);
        if (param.isSyncAll()) {
            System.out.println("同步完成并删除redis key");
            redisTemplate.delete(SYNC_MENU_KEY);
        }
        return new Result<>(true);
    }

}
