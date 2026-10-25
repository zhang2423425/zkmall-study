/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.exception;

import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
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
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = CoBusinessException.class)
    @ResponseBody
    public Result<?> coBusinessExceptionHandler(CoBusinessException e) {
        log.error(" catch CoBusinessException {}", e.getMessage(), e);
        Result<?> result = new Result<>(e.getCode());
        if (!EmptyUtils.isEmpty(e.getErrorData())) {
            result.setErrorData(e.getErrorData());
        }
        if (StringUtils.isNotBlank(e.getMessage())) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> exceptionHandler(Exception e) {
        log.error("系统异常:", e);
        return new Result<>(CoReturnFormat.SYS_ERROR);
    }
}
