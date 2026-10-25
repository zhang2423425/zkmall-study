/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.exception;

import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 *
 * @author zkmall zkmallvip@163.com
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(Exception e) {
        log.error("系统异常:", e);
        if (e instanceof CoBusinessException exs) {
            return new Result<>(exs.getCode(), exs);
        }
        if (e instanceof MethodArgumentNotValidException exs) {
            //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            for (FieldError error : exs.getBindingResult().getFieldErrors()) {
                return new Result<>(error.getDefaultMessage());
            }
        }
        Result<String> result = new Result<>();
        result.setCode(CoReturnFormat.SYS_ERROR);
        result.setMessage(e.getMessage());
        return result;
    }
}
