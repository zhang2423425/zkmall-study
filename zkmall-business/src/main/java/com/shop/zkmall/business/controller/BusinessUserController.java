/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.user.Business;
import com.shop.zkmall.business.param.business.BusinessLoginParam;
import com.shop.zkmall.business.param.user.*;
import com.shop.zkmall.business.service.business.CerePlatformBusinessService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.RandomStringUtil;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理")
public class BusinessUserController {

    private final CerePlatformBusinessService cerePlatformBusinessService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final SmsStrategy smsStrategy;

    @Value("${globalAdminPhone}")
    private String globalAdminPhone;

    /* 记录发送二次隐私认证的验证码的时间 */
    private static final String PRIVACY_CODE_SEND_TIME = "business_privacy_code_send_time_";

    /* 超过次数 */
    private static final String PRIVACY_BEYOND_COUNT = "business_privacy_beyond_count_";

    /* 隐私开关 */
    private static final String PRIVACY_SHOW_SWITCH = "business_privacy_show_switch_";

    /* 记录发送修改管理员手机号的验证码的时间 */
    private static final String UPDATE_PHONE_CODE_SEND_TIME = "business_update_phone_code_send_time";

    /* 修改管理员手机号-发送验证码-超过次数 */
    private static final String UPDATE_PHONE_BEYOND_COUNT = "business_update_phone_beyond_count";

    /**
     * 添加用户
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加用户")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated BusinessSaveUser business, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        business.setShopId(user.getShopId());
        cerePlatformBusinessService.save(business, user);
        return new Result<>(user.getUsername(), "添加用户", GsonUtil.objectToGson(business));
    }

    /**
     * 修改用户
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改用户")
    @WebRequestLog
    public Result<String> update(@RequestBody BusinessUpdateUser business, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        interceptUpdate(business);
        cerePlatformBusinessService.update(business, user);
        return new Result<>(user.getUsername(), "修改用户", GsonUtil.objectToGson(business));
    }

    private void interceptUpdate(BusinessUpdateUser business) {
        if (business != null && business.getBusinessUserId() != null && business.getBusinessUserId() == 125) {
            throw new CoBusinessException("演示环境不允许修改默认用户信息");
        }
    }

    /**
     * 删除用户
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除用户")
    @WebRequestLog
    public Result<String> delete(@RequestBody BusinessDeleteUser param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        ContextUtil.interceptDelete(user);
        cerePlatformBusinessService.delete(param, user);
        return new Result<>(user.getUsername(), "删除用户", GsonUtil.objectToGson(param));
    }

    /**
     * 修改用户查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "修改用户查询")
    public Result<Business> getById(@RequestBody BusinessDeleteUser param) throws Exception {
        Business business = cerePlatformBusinessService.getById(param.getBusinessUserId());
        return new Result<>(business);
    }

    /**
     * 用户管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "用户管理查询")
    @NoRepeatSubmit
    public Result<Page<CerePlatformBusiness>> getAll(@RequestBody BusinessGetAllUser param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page<CerePlatformBusiness> page = cerePlatformBusinessService.getAll(param);
        return new Result<>(page);
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
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        if (user == null) {
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        String phone = cerePlatformBusinessService.findAdminPhone(user.getShopId());
        if (phone == null) {
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        result.setData(phone);
        return result;
    }

    /**
     * 获取隐私短信验证码
     */
    @PostMapping(value = "getPrivacyCode")
    @Operation(summary = "获取隐私短信验证码")
    public Result<Boolean> getPrivacyCode(@RequestBody UserGetCodeParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        if (user == null) {
            return new Result<>(false);
        }
        //获取验证码
        String code = RandomStringUtil.getRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        //查询超时记录
        String beyondCountKey = PRIVACY_BEYOND_COUNT + user.getBusinessUserId();
        Integer beyondCount = (Integer) redisTemplate.opsForValue().get(beyondCountKey);
        if (beyondCount != null && beyondCount == 1) {
            throw new CoBusinessException(CoReturnFormat.SEND_CODE_ERR);
        }
        String sendTimeKey = PRIVACY_CODE_SEND_TIME + user.getBusinessUserId();
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
     * 验证二次隐私开启
     *
     * @param param 封装json对象
     */
    @PostMapping(value = "verifyPrivacyCode")
    @Operation(summary = "验证二次隐私开启")
    public Result<Boolean> verifyPrivacyCode(@RequestBody BusinessLoginParam param, HttpServletRequest request) {
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
            //获取当前登录账户
            CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
            if (user == null) {
                return new Result<>(false);
            }
            redisTemplate.opsForValue().set(PRIVACY_SHOW_SWITCH + user.getBusinessUserId(), System.currentTimeMillis(), Duration.ofHours(24));
        } else {
            result = false;
        }
        return new Result<>(result);
    }

    /**
     * 获取更新管理员手机号的短信验证码
     */
    @PostMapping(value = "getUpdatePhoneCode")
    @Operation(summary = "获取更新管理员手机号的短信验证码")
    public Result<Boolean> getUpdatePhoneCode(@RequestBody UserGetCodeParam user) {
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
        return new Result<>(true);
    }

    /**
     * 更新管理员手机号
     */
    @PostMapping(value = "updatePhone")
    @Operation(summary = "更新管理员手机号")
    public Result<Boolean> updatePhone(@RequestBody UserUpdatePhoneParam param, HttpServletRequest request) {
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
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
            String code = (String) redisTemplate.opsForValue().get(param.getPhone());
            if (!param.getCode().equals(code)) {
                return new Result<>(CoReturnFormat.CODE_ERROR);
            }
            cerePlatformBusinessService.updatePhone(param, user);
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
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        if (user != null) {
            Long switchTimestamp = (Long) redisTemplate.opsForValue().get(PRIVACY_SHOW_SWITCH + user.getBusinessUserId());
            if (switchTimestamp != null && switchTimestamp > 0) {
                return new Result<>(switchTimestamp);
            }
        }
        return new Result<>(0L);
    }
}
