/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.admin.page.permission.MenuButton;
import com.shop.zkmall.admin.param.user.UserBuildParam;
import com.shop.zkmall.admin.param.user.UserForgetPasswordParam;
import com.shop.zkmall.admin.param.user.UserGetCodeParam;
import com.shop.zkmall.admin.param.user.UserLoginParam;
import com.shop.zkmall.admin.redis.service.UserRedisService;
import com.shop.zkmall.admin.service.permission.CerePlatformPermissionService;
import com.shop.zkmall.admin.service.redis.CereRedisKeyService;
import com.shop.zkmall.admin.service.shop.CereShopCheckService;
import com.shop.zkmall.admin.service.user.CerePlatformUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.*;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "用户登录模块", description = "用户登录模块")
public class AdminController {

    private final HttpServletRequest request;

    private final CerePlatformUserService cerePlatformUserService;

    private final CerePlatformPermissionService cerePlatformPermissionService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final SmsStrategy smsStrategy;

    private final CereRedisKeyService cereRedisKeyService;

    private final UserRedisService userRedisService;

    private final CereShopCheckService cereShopCheckService;

    @Value("${globalAdminPhone}")
    private String globalAdminPhone;

    /* 记录发送二次隐私认证的验证码的时间 */
    private static final String PRIVACY_CODE_SEND_TIME = "privacy_code_send_time_";

    /* 超过次数 */
    private static final String PRIVACY_BEYOND_COUNT = "privacy_beyond_count_";

    /* 隐私开关 */
    private static final String PRIVACY_SHOW_SWITCH = "privacy_show_switch_";

    /* 记录发送修改管理员手机号的验证码的时间 */
    private static final String UPDATE_PHONE_CODE_SEND_TIME = "update_phone_code_send_time";

    /* 修改管理员手机号-发送验证码-超过次数 */
    private static final String UPDATE_PHONE_BEYOND_COUNT = "update_phone_beyond_count";

    /**
     * 平台账户登录
     *
     * @param param 封装json对象
     */
    @PostMapping(value = "login")
    @Operation(summary = "平台账户登录")
    public Result<CerePlatformUser> login(@RequestBody UserLoginParam param) {
        //登录账号和密码解密
        try {
            if (param.getUsername().length() > 15) {
                param = param.decrypt();
            }
            //com.shop.zkmall.admin.param = com.shop.zkmall.admin.param.decrypt();
        } catch (Exception e) {
            return new Result<>(CoReturnFormat.PARAM_INVALID);
        }
        CerePlatformUser user;
        //有验证码
        if (!EmptyUtils.isEmpty(param.getCode())) {
            ValidationUtil.checkValidateCode(param.getUsername(), param.getCode());
            if (noDemoAccount(param)) {
                //手机号登录,校验验证码
                String code = (String) redisTemplate.opsForValue().get(param.getUsername());
                if (!param.getCode().equals(code)) {
                    return new Result<>(CoReturnFormat.CODE_ERROR);
                }
            }
            //根据手机号查询账户信息
            user = cerePlatformUserService.findByPhone(param.getUsername());
            log.info("user: {}", user);
        } else {
            //无验证码
            //根据账号查询token
            user = cerePlatformUserService.findByUserName(param.getUsername());
            if (user != null) {
                //校验密码
                if (!param.getPassword().equals(EncryptUtil.decrypt(user.getPassword()))) {
                    return new Result<>(CoReturnFormat.USER_OR_PASSWD_ERROR);
                }
            }
        }
        if (user == null) {
            return new Result<>(CoReturnFormat.USER_UNREGISTER);
        }
        if (IntegerEnum.NO.getCode().equals(user.getState())) {
            return new Result<>(CoReturnFormat.USER_TYPE_STOP);
        }

        String token;
        if (ObjectUtil.isNotNull(param.getRememberMe()) && param.getRememberMe()) {
            //记住我 不重新生成token
            token = userRedisService.getToken(user.getPlatformUserId());
            if (ObjectUtil.isNull(token)) {
                token = userRedisService.createToken4Remember(param.getUsername(), user.getPlatformUserId());
            }
            user.setToken(token);
            log.info("rememberMe token: {}", token);
        } else {
            //不记住我 重新生成token
            token = userRedisService.createToken(param.getUsername(), user.getPlatformUserId());
            log.info("not rememberMe token: {}", token);
        }
        user.setToken(token);
        request.setAttribute("user", user);
        return new Result<>(user);
    }

    private boolean noDemoAccount(UserLoginParam param) {
        CerePlatformUser user = cerePlatformUserService.findById(1L);
        return !user.getPhone().equals(param.getUsername());
    }

    /**
     * 登录权限查询
     */
    @PostMapping(value = "build")
    @Operation(summary = "登录权限查询")
    public Result<List<MenuButton>> build(@RequestBody UserBuildParam user) {
        List<MenuButton> list = cerePlatformPermissionService.getAllByUserId(user);
        return new Result<>(list);
    }

    /**
     * 忘记密码
     */
    @PostMapping(value = "forgetPassword")
    @NoRepeatSubmit
    @Operation(summary = "忘记密码")
    public Result<String> forgetPassword(@RequestBody UserForgetPasswordParam user) {
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
        CerePlatformUser cerePlatformUser = cerePlatformUserService.findByPhone(user.getUsername());
        if (cerePlatformUser == null) {
            return new Result<>(CoReturnFormat.USER_UNREGISTER);
        }
        cerePlatformUserService.forgetPassword(user, cerePlatformUser);
        return new Result<>();
    }


    /**
     * 获取短信验证码
     */
    @PostMapping(value = "getCode")
    @Operation(summary = "获取短信验证码")
    public Result<String> getCode(@RequestBody UserGetCodeParam user) {
        String ip = HttpUtils.getClientIp(request);
        redisTemplate.delete(CaptchaController.CAPTCHA_PREFIX + ip);

        //获取验证码
        String code = RandomStringUtil.getRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        //验证码存到redis中(5分钟失效)
        redisTemplate.opsForValue().set(user.getPhone(), code, Duration.ofMinutes(5));
        //发送短信给用户
        try {
            smsStrategy.sendNotice(user.getPhone(), map);
        } catch (Exception e) {
            log.error("getCode fail: phone = {}", user.getPhone(), e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        return new Result<>();
    }

    /**
     * 获取管理员手机号
     */
    @PostMapping(value = "getAdminPhone")
    @Operation(summary = "获取管理员手机号")
    public Result<String> getAdminPhone(HttpServletRequest request) {
        Result<String> result = new Result<>();
        if (StringUtils.isNotBlank(globalAdminPhone)) {
            result.setData(globalAdminPhone);
            return result;
        }
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        if (user == null) {
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        String phone = cerePlatformUserService.getAdminPhone();

        if (phone == null) {
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        //String desensitizationPhone = com.shop.zkmall.commons.utils.StringUtils.showStartAndEnd(phone,3,4);
        result.setData(phone);
        return result;
    }

    /**
     * 获取隐私短信验证码
     */
    @PostMapping(value = "getPrivacyCode")
    @Operation(summary = "获取隐私短信验证码")
    public Result<Boolean> getPrivacyCode(@RequestBody UserGetCodeParam param, HttpServletRequest request) {
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        if (user == null) {
            return new Result<>(false);
        }
        //获取验证码
        String code = RandomStringUtil.getRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        //查询超时记录
        String beyondCountKey = PRIVACY_BEYOND_COUNT + user.getPlatformUserId();
        Integer beyondCount = (Integer) redisTemplate.opsForValue().get(beyondCountKey);
        if (beyondCount != null && beyondCount == 1) {
            throw new CoBusinessException(CoReturnFormat.SEND_CODE_ERR);
        }
        String sendTimeKey = PRIVACY_CODE_SEND_TIME + user.getPlatformUserId();
        //存储当前发送的时间
        String privacyCodeSendTime = (String) redisTemplate.opsForValue().get(sendTimeKey);
        log.info("privacyCodeSendTime: {}", privacyCodeSendTime);
        if (StringUtils.isNotBlank(privacyCodeSendTime)) {
            String[] timeArr = privacyCodeSendTime.split(",");
            if (timeArr.length >= 3) {
                log.info("timeArr.length >= 3");
                long compareTime = Long.parseLong(timeArr[timeArr.length - 3]);
                log.info("compareTime {}", compareTime);
                if (System.currentTimeMillis() - compareTime <= 30 * 60 * 1000) {
                    //超过之后，需要设置1小时不可再发送
                    redisTemplate.opsForValue().set(beyondCountKey, 1, Duration.ofHours(1));
                    throw new CoBusinessException(CoReturnFormat.SEND_CODE_ERR);
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = timeArr.length - 2; i < timeArr.length; i++) {
                        builder.append(timeArr[i]).append(",");
                    }
                    builder.append(System.currentTimeMillis());
                    privacyCodeSendTime = builder.toString();
                }
            } else {
                privacyCodeSendTime = privacyCodeSendTime + "," + System.currentTimeMillis();
            }
        } else {
            privacyCodeSendTime = System.currentTimeMillis() + "";
        }
        //发送短信给用户
        try {
            //验证码存到redis中(5分钟失效)
            redisTemplate.opsForValue().set(param.getPhone(), code, Duration.ofMinutes(5));
            smsStrategy.sendNotice(param.getPhone(), map);
            log.info("privacyCodeSendTime save: {}", privacyCodeSendTime);
            redisTemplate.opsForValue().set(sendTimeKey, privacyCodeSendTime);
        } catch (Exception e) {
            log.error("getPrivacyCode fail: phone = {}", param.getPhone(), e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        return new Result<>(true);
    }

    /**
     * 获取更新管理员手机号的短信验证码
     */
    @PostMapping(value = "getUpdatePhoneCode")
    @Operation(summary = "获取更新管理员手机号的短信验证码")
    public Result<String> getUpdatePhoneCode(@RequestBody UserGetCodeParam user) {
        //获取验证码
        String code = RandomStringUtil.getRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        //查询超时记录
        Integer beyondCount = (Integer) redisTemplate.opsForValue().get(UPDATE_PHONE_BEYOND_COUNT);
        if (beyondCount != null && beyondCount == 1) {
            throw new CoBusinessException(CoReturnFormat.SEND_CODE_ERR);
        }
        //存储当前发送的时间
        String updatePhoneCodeSendTime = (String) redisTemplate.opsForValue().get(UPDATE_PHONE_CODE_SEND_TIME);
        if (StringUtils.isNotBlank(updatePhoneCodeSendTime)) {
            String[] timeArr = updatePhoneCodeSendTime.split(",");
            if (timeArr.length >= 3) {
                long compareTime = Long.parseLong(timeArr[timeArr.length - 3]);
                if (System.currentTimeMillis() - compareTime <= 30 * 60 * 1000) {
                    //超过之后，需要设置1小时不可再发送
                    redisTemplate.opsForValue().set(UPDATE_PHONE_BEYOND_COUNT, 1, Duration.ofHours(1));
                    throw new CoBusinessException(CoReturnFormat.SEND_CODE_ERR);
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = timeArr.length - 2; i < timeArr.length; i++) {
                        builder.append(timeArr[i]).append(",");
                    }
                    builder.append(System.currentTimeMillis());
                    updatePhoneCodeSendTime = builder.toString();
                }
            } else {
                updatePhoneCodeSendTime = updatePhoneCodeSendTime + "," + System.currentTimeMillis();
            }
        } else {
            updatePhoneCodeSendTime = System.currentTimeMillis() + "";
        }
        //发送短信给用户
        try {
            //验证码存到redis中(5分钟失效)
            redisTemplate.opsForValue().set(user.getPhone(), code, Duration.ofMinutes(5));
            smsStrategy.sendNotice(user.getPhone(), map);
            redisTemplate.opsForValue().set(UPDATE_PHONE_CODE_SEND_TIME, updatePhoneCodeSendTime);
        } catch (Exception e) {
            log.error("getUpdatePhoneCode fail: phone = {}", user.getPhone(), e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        return new Result<>();
    }

    /**
     * 验证二次隐私开启
     *
     * @param param 封装json对象
     */
    @PostMapping(value = "verifyPrivacyCode")
    @Operation(summary = "验证二次隐私开启")
    public Result<Boolean> verifyPrivacyCode(@RequestBody UserLoginParam param, HttpServletRequest request) {
        //登录账号和密码解密
        try {
            if (param.getUsername().length() > 15) {
                param = param.decrypt();
            }
            //com.shop.zkmall.admin.param = com.shop.zkmall.admin.param.decrypt();
        } catch (Exception e) {
            return new Result<>(CoReturnFormat.PARAM_INVALID);
        }
        boolean result = true;
        if (!EmptyUtils.isEmpty(param.getCode())) {
            //手机号登录,校验验证码
            String code = (String) redisTemplate.opsForValue().get(param.getUsername());
            if (!param.getCode().equals(code)) {
                return new Result<>(CoReturnFormat.CODE_ERROR);
            }
            CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
            if (user == null) {
                return new Result<>(false);
            }
            redisTemplate.opsForValue().set(PRIVACY_SHOW_SWITCH + user.getPlatformUserId(), System.currentTimeMillis(), Duration.ofHours(24));
        } else {
            result = false;
        }
        return new Result<>(result);
    }

    /**
     * 一键初始化redis延时任务
     */
    @PostMapping(value = "initializationRedis")
    @NoRepeatSubmit
    @Operation(summary = "一键初始化redis延时任务")
    public Result<String> initializationRedis() throws Exception {
        cereRedisKeyService.initializationRedis();
        return new Result<>();
    }

    /**
     * 初始化店铺角色权限数据
     */
    @PostMapping(value = "initializationShopPermission")
    @NoRepeatSubmit
    @Operation(summary = "初始化店铺角色权限数据")
    public Result<String> initializationShopPermission() {
        cereShopCheckService.setInitPermission(2L, TimeUtils.yyMMddHHmmss());
        return new Result<>();
    }

    @GetMapping(value = "getSwaggerConfig")
    @Operation(summary = "字典下拉数据查询")
    public JSONObject getSwaggerConfig() {
        return JSONObject.parseObject("{\"configUrl\":\"/v3/api-docs/swagger-config\",\"oauth2RedirectUrl\":\"http://localhost/api/swagger-ui/oauth2-redirect.html\",\"operationsSorter\":\"alpha\",\"tagsSorter\":\"alpha\",\"urls\":[{\"url\":\"/adminapi/v3/api-docs/default\",\"name\":\"default\"}],\"validatorUrl\":\"\"}");
    }

}
