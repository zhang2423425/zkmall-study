/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.user.PlatformUser;
import com.shop.zkmall.admin.param.user.*;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("platform_user")
@RequiredArgsConstructor
@Tag(name = "用户模块", description = "用户模块")
public class PlatformUserController {

    private final CerePlatformUserService cerePlatformUserService;

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PRIVACY_SHOW_SWITCH = "privacy_show_switch_";

    /**
     * 添加账户
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加账户")
    @WebRequestLog
    public Result<String> insert(@RequestBody @Validated UserSaveParam userParam, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.save(userParam, user);
/*
        user=new CerePlatformUser();//
        user.setName("我加的");//
*/
        return new Result<>(user.getUsername(), "添加账户", GsonUtil.objectToGson(userParam));
    }

    /**
     * 修改账户
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改账户")
    @WebRequestLog
    public Result<String> update(@RequestBody UserUpdateParam userParam, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.update(userParam, user);
        return new Result<>(user.getUsername(), "修改账户", GsonUtil.objectToGson(userParam));
    }

    /**
     * 删除账户
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除账户")
    @WebRequestLog
    public Result<String> delete(@RequestBody UserDeleteParam userParam, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.delete(userParam, user);
        return new Result<>(user.getUsername(), "删除账户", GsonUtil.objectToGson(userParam));
    }

    /**
     * 账户编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "账户编辑查询")
    public Result<PlatformUser> getById(@RequestBody UserGetByIdParam userParam) throws Exception {
        PlatformUser cerePlatformUser = cerePlatformUserService.getById(userParam.getPlatformUserId());
        return new Result<>(cerePlatformUser);
    }

    /**
     * 账户管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "账户管理查询")
    public Result<Page<PlatformUser>> getAll(@RequestBody UserGetAllParam userParam) {
        Page<PlatformUser> page = cerePlatformUserService.getAll(userParam);
        return new Result<>(page);
    }

    /**
     * 更新密码
     */
    @PostMapping(value = "updatePassword")
    @Operation(summary = "更新密码")
    public Result<String> updatePassword(@RequestBody UserForgetPasswordParam passwordParam, HttpServletRequest request) {
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformUserService.updatePassword(passwordParam, user);
        return new Result<>();
    }

    /**
     * 更新管理员手机号
     */
    @PostMapping(value = "updatePhone")
    @Operation(summary = "更新管理员手机号")
    public Result<Boolean> updatePhone(@RequestBody UserUpdatePhoneParam param, HttpServletRequest request) {
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        boolean result = true;
        try {
            if (param.getPhone().length() > 15) {
                param = param.decrypt();
            }
            //com.shop.zkmall.admin.param = com.shop.zkmall.admin.param.decrypt();
        } catch (Exception e) {
            return new Result<>(CoReturnFormat.PARAM_INVALID);
        }
        if (!EmptyUtils.isEmpty(param.getCode())) {
            //手机号登录,校验验证码
            log.info("code: {}", param.getCode());
            String code = (String) redisTemplate.opsForValue().get(param.getPhone());
            log.info("redis code: {}", code);
            if (!param.getCode().equals(code)) {
                log.info("not equals");
                return new Result<>(CoReturnFormat.CODE_ERROR);
            }
            cerePlatformUserService.updatePhone(param, user);
        } else {
            result = false;
        }
        return new Result<>(result);
    }

    /**
     * 查询当前用户是否进行过二次认证
     */
    @PostMapping(value = "getPrivacySwitch")
    @Operation(summary = "查询当前用户是否进行过二次认证")
    public Result<Long> getPrivacySwitch(HttpServletRequest request) {
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        if (user != null) {
            Long switchTimestamp = (Long) redisTemplate.opsForValue().get(PRIVACY_SHOW_SWITCH + user.getPlatformUserId());
            if (switchTimestamp != null && switchTimestamp > 0) {
                return new Result<>(switchTimestamp);
            }
        }
        return new Result<>(0L);
    }

    /**
     * 更新头像
     */
    @PostMapping(value = "updateAvatar")
    @Operation(summary = "更新头像")
    public Result<String> updateAvatar(@RequestBody CerePlatformUser platformUser, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        if (user != null) {
            user.setAvatar(platformUser.getAvatar());
            cerePlatformUserService.updateAvatar(user);
        }
        return new Result<>();
    }
}
