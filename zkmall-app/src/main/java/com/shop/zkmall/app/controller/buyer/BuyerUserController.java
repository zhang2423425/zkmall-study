/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.buyer;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.app.page.buyer.MyUser;
import com.shop.zkmall.app.param.buyer.UserParam;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 个人信息模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "个人信息模块", description = "个人信息模块")
public class BuyerUserController {

    private final CereBuyerUserService cereBuyerUserService;


    /**
     * 个人系信息查询
     */
    @GetMapping("getUser")
    @NoRepeatSubmit
    @Operation(summary = "个人系信息查询")
    public Result<MyUser> getUser(HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        MyUser buyerUser = cereBuyerUserService.getUser(user.getBuyerUserId());
        return new Result<>(buyerUser, CoReturnFormat.SUCCESS);
    }

    /**
     * 注销账户
     */
    @DeleteMapping("/delUser/{code}")
    @NoRepeatSubmit
    @Operation(summary = "注销账户")
    public Result<String> closeAccount(@PathVariable("code") String verificationCode, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        ValidationUtil.checkValidateCode(user.getPhone(), verificationCode);
        cereBuyerUserService.delete(user);
        return new Result<>(CoReturnFormat.SUCCESS);
    }


    /**
     * 修改个人信息
     */
    @RequestMapping(value = "update", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "修改个人信息")
    @WebRequestLog
    public Result<String> update(@RequestBody CereBuyerUser buyerUser, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        buyerUser.setBuyerUserId(user.getBuyerUserId());
        cereBuyerUserService.update(buyerUser, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "修改个人信息", GsonUtil.objectToGson(buyerUser));
    }

    /**
     * 身份验证
     */
    @GetMapping("checkUser")
    @Operation(summary = "身份验证")
    @WebRequestLog
    public Result<CereBuyerUser> checkUser(UserParam param, HttpServletRequest request) {
        ValidationUtil.checkValidateCode(param.getPhone(), param.getVerificationCode());
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        return new Result<>(user, CoReturnFormat.SUCCESS, user.getWechatName(), "身份验证", GsonUtil.objectToGson(param));
    }


    /**
     * 更换绑定手机号
     */
    @RequestMapping(value = "updatePhone", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "更换绑定手机号")
    @WebRequestLog
    public Result<String> updatePhone(@RequestBody UserParam param, HttpServletRequest request) {
        ValidationUtil.checkValidateCode(param.getPhone(), param.getVerificationCode());
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerUserService.updatePhone(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "更换绑定手机号", GsonUtil.objectToGson(param));
    }

    /**
     * 解除绑定手机号
     */
    @RequestMapping(value = "relievePhone", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "解除绑定手机号")
    @WebRequestLog
    public Result<String> relievePhone(@RequestBody UserParam param, HttpServletRequest request) {
        ValidationUtil.checkValidateCode(param.getPhone(), param.getVerificationCode());
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerUserService.relievePhone(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "解除绑定手机号", GsonUtil.objectToGson(param));
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "updatePassword", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "修改密码")
    @WebRequestLog
    public Result<String> updatePassword(@RequestBody UserParam param, HttpServletRequest request) {
        //校验两次密码是否一致
        if (!param.getPassword().equals(param.getNewPassword())) {
            return new Result<>(CoReturnFormat.PASSWORD_NOT_AGREEN);
        }
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereBuyerUserService.updatePassword(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "修改密码", GsonUtil.objectToGson(param));
    }

    /**
     * 埋点上报
     * 逻辑暂时不可用
     */
    @PostMapping("trackReport")
    @Operation(summary = "埋点上报")
    public Result<Boolean> trackReport() {
        return new Result<>(true, CoReturnFormat.SUCCESS);
    }

}
