/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.business.page.permission.MenuButton;
import com.shop.zkmall.business.page.shop.PlatformBusiness;
import com.shop.zkmall.business.param.business.BusinessForgetParam;
import com.shop.zkmall.business.param.business.BusinessGetCodeParam;
import com.shop.zkmall.business.param.business.BusinessLoginParam;
import com.shop.zkmall.business.param.business.DirOrFileParam;
import com.shop.zkmall.business.param.permission.UserBuildParam;
import com.shop.zkmall.business.redis.service.api.UserRedisService;
import com.shop.zkmall.business.service.business.CerePlatformBusinessService;
import com.shop.zkmall.business.service.permission.CerePlatformPermissionService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.*;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户登录
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("business")
@RequiredArgsConstructor
@Tag(name = "用户登录模块", description = "用户登录模块")
public class BusinessController {

    private final HttpServletRequest request;

    private final CerePlatformBusinessService cerePlatformBusinessService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final SmsStrategy smsStrategy;

    private final CerePlatformPermissionService cerePlatformPermissionService;

    private final UserRedisService userRedisService;

    /**
     * 商家用户登录
     *
     * @param param 封装json对象
     */
    @PostMapping(value = "login")
    @Operation(summary = "商家用户登录")
    public Result<PlatformBusiness> login(@RequestBody BusinessLoginParam param) {
        //登录账号和密码解密
        try {
            if (param.getUsername().length() > 15) {
                param = param.decrypt();
            }
            //com.shop.zkmall.admin.param = com.shop.zkmall.admin.param.decrypt();
        } catch (Exception e) {
            return new Result<>(CoReturnFormat.PARAM_INVALID);
        }
        PlatformBusiness user;
        if (!EmptyUtils.isEmpty(param.getCode())) {
            ValidationUtil.checkValidateCode(param.getUsername(), param.getCode());
            //根据手机号查询用户信息
            user = cerePlatformBusinessService.findByPhone(param.getUsername());
        } else {
            //根据账号查询token
            user = cerePlatformBusinessService.findByPhone(param.getUsername());
            if (user == null) {
                user = cerePlatformBusinessService.findByUserName(param.getUsername());
            }
            if (user != null) {
                //校验密码
                if (!param.getPassword().equals(EncryptUtil.decrypt(user.getPassword()))) {
                    return new Result<>(CoReturnFormat.USER_OR_PASSWD_ERROR);
                }
            }
        }
        if (user == null) {
            return new Result<>(CoReturnFormat.USER_UNREGISTER);
        } else {
            if (IntegerEnum.NO.getCode().equals(user.getState())) {
                return new Result<>(CoReturnFormat.USER_TYPE_STOP);
            }
            if (IntegerEnum.UNTREATED.getCode().equals(user.getCheckState())) {
                return new Result<>(CoReturnFormat.SHOP_CHECK_STAY);
            }
            if (IntegerEnum.REFUSE.getCode().equals(user.getCheckState())) {
                return new Result<>(CoReturnFormat.SHOP_CHECK_STOP);
            }
        }
        String token;
        if (ObjectUtil.isNotNull(param.getRememberMe()) && param.getRememberMe()) {
            //记住登录 不更新token
            token = userRedisService.getToken(user.getBusinessUserId());
            if (ObjectUtil.isNull(token)) {
                token = userRedisService.createToken4Remember(param.getUsername(), user.getBusinessUserId());
            }
        } else {
            //默认重新生成token
            token = userRedisService.createToken(param.getUsername(), user.getBusinessUserId());
        }
        user.setToken(token);
        request.setAttribute("user", user);
        return new Result<>(user);
    }


    /**
     * 登录权限查询
     */
    @PostMapping(value = "build")
    @Operation(summary = "登录权限查询")
    public Result<List<MenuButton>> build(@RequestBody UserBuildParam user) {
        CerePlatformBusiness cacheUser = (CerePlatformBusiness) request.getAttribute("user");
        user.setShopId(ContextUtil.getShopId());
        user.setBusinessUserId(cacheUser.getBusinessUserId());
        List<MenuButton> list = cerePlatformPermissionService.getAllByUserId(user);
        return new Result<>(list);
    }

    /**
     * 忘记密码
     */
    @PostMapping(value = "forgetPassword")
    @NoRepeatSubmit
    @Operation(summary = "忘记密码")
    public Result<String> forgetPassword(@RequestBody BusinessForgetParam user) {
        try {
            user = user.decrypt();
        } catch (Exception e) {
            return new Result<>(CoReturnFormat.PARAM_INVALID);
        }
        //校验验证码
        ValidationUtil.checkValidateCode(user.getUsername(), user.getCode());
        //校验2次密码是否一致
        if (!user.getPassword().equals(user.getNewPassword())) {
            return new Result<>(CoReturnFormat.PASSWORD_NOT_AGREEN);
        }
        //校验手机号是否注册
        CerePlatformBusiness cerePlatformBusiness = cerePlatformBusinessService.findByUserName(user.getUsername());
        if (cerePlatformBusiness == null) {
            return new Result<>(CoReturnFormat.USER_UNREGISTER);
        }
        cerePlatformBusinessService.forgetPassword(user, cerePlatformBusiness);
        return new Result<>();
    }


    /**
     * 获取短信验证码
     */
    @PostMapping(value = "getCode")
    @Operation(summary = "获取短信验证码")
    public Result<String> getCode(@RequestBody BusinessGetCodeParam user) {
        String ip = HttpUtils.getClientIp(request);
        String captcha = (String) redisTemplate.opsForValue().get(CaptchaController.CAPTCHA_PREFIX + ip);
        if (captcha == null || !captcha.equals(user.getCode())) {
            //throw new CoBusinessException(CoReturnFormat.CAPTCHA_ERROR);
        }
        redisTemplate.delete(CaptchaController.CAPTCHA_PREFIX + ip);

        //获取验证码
        String code = RandomStringUtil.getRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        //验证码存到redis中(5分钟失效)
        redisTemplate.opsForValue().set(user.getPhone(), code, Duration.ofMinutes(5));
        //发送短信给用户
        try {
            //验证账号可用
            PlatformBusiness shopUser = cerePlatformBusinessService.findByPhone(user.getPhone());
            if (shopUser == null) {
                return new Result<>(CoReturnFormat.USER_UNREGISTER);
            } else {
                if (IntegerEnum.NO.getCode().equals(shopUser.getState())) {
                    return new Result<>(CoReturnFormat.USER_TYPE_STOP);
                }
                if (IntegerEnum.UNTREATED.getCode().equals(shopUser.getCheckState())) {
                    return new Result<>(CoReturnFormat.SHOP_CHECK_STAY);
                }
                if (IntegerEnum.REFUSE.getCode().equals(shopUser.getCheckState())) {
                    return new Result<>(CoReturnFormat.SHOP_CHECK_STOP);
                }
            }
            smsStrategy.sendNotice(user.getPhone(), map);
        } catch (Exception e) {
            log.error("getCode fail: phone = {}", user.getPhone(), e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        return new Result<>();
    }

    /**
     * 更新密码
     */
    @PostMapping(value = "updatePassword")
    @Operation(summary = "更新密码")
    public Result<String> updatePassword(@RequestBody BusinessForgetParam passwordParam, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cerePlatformBusinessService.updatePassword(passwordParam, user);
        return new Result<>();
    }

    /**
     * 更新头像
     */
    @PostMapping(value = "updateAvatar")
    @Operation(summary = "更新头像")
    public Result<String> updateAvatar(@RequestBody CerePlatformBusiness platformBusiness, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        if (user != null) {
            user.setAvatar(platformBusiness.getAvatar());
            cerePlatformBusinessService.updateAvatar(user);
        }
        return new Result<>();
    }

    /**
     * 查看目录下的文件
     */
    @PostMapping(value = "listDir")
    @Operation(summary = "查看目录下的文件")
    public Result<String> listDir(@RequestBody DirOrFileParam param) {
        try {
            File f = new File(param.getDir());
            log.info("absolute path {}", f.getAbsolutePath());
            String[] filelist = f.list();
            if (filelist != null) {
                for (String i : filelist) {
                    log.info("file {}", i);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new Result<>();
    }

    /**
     * 复制文件
     */
    @PostMapping(value = "copyFile")
    @Operation(summary = "复制文件")
    public Result<String> copyFile(@RequestBody DirOrFileParam param) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        try {
            File source = new File(param.getSourceFile());
            File dest = new File(param.getTargetFile());

            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
        return new Result<>();
    }

    @GetMapping(value = "getSwaggerConfig")
    @Operation(summary = "字典下拉数据查询")
    public JSONObject getSwaggerConfig() {
        return JSONObject.parseObject("{\"configUrl\":\"/v3/api-docs/swagger-config\",\"oauth2RedirectUrl\":\"http://localhost/api/swagger-ui/oauth2-redirect.html\",\"operationsSorter\":\"alpha\",\"tagsSorter\":\"alpha\",\"urls\":[{\"url\":\"/businessapi/v3/api-docs/default\",\"name\":\"default\"}],\"validatorUrl\":\"\"}");
    }
}
