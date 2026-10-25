/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.result;


import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "Result", description = "通用请求返回数据")
public class Result<T> {

    public Result() {
        this.code = "";
        this.message = "";
    }

    public Result(T data) {
        this.code = "";
        this.data = data;
    }

    public Result(T data, String name, String describe, String json) {
        this.code = "";
        this.data = data;
        this.name = name;
        this.describe = describe;
        this.json = json;
    }

    public Result(String name, String describe, String json) {
        this.code = "";
        this.message = "";
        this.name = name;
        this.describe = describe;
        this.json = json;
    }

    public Result(T data, String code) {
        this.code = code;
        this.message = CoReturnFormat.getMessage(code);
        this.data = data;
    }

    public Result(T data, String code, String name, String describe, String json) {
        this.code = code;
        this.message = CoReturnFormat.getMessage(code);
        this.data = data;
        this.name = name;
        this.describe = describe;
        this.json = json;
    }

    public Result(String code, CoBusinessException e) {
        this.code = code;
        this.message = e.getMessage();
        this.errorData = e.getErrorData();
    }

    public Result(String code, Object data) {
        this.code = code;
        this.message = CoReturnFormat.getMessage(code);
        this.errorData = data;
    }

    public Result(String code) {
        this.code = code;
        this.message = CoReturnFormat.getMessage(code);
    }

    public Result(String code, String name, String describe, String json) {
        this.code = code;
        this.message = CoReturnFormat.getMessage(code);
        this.name = name;
        this.describe = describe;
        this.json = json;
    }

    /**
     * 返回编码code
     */
    @Schema(description = "返回编码code")
    private String code;

    /**
     * 返回数据data
     */
    @Schema(description = "返回数据data")
    private T data;

    /**
     * 错误数据
     */
    @Schema(description = "错误数据")
    private Object errorData;

    /**
     * 返回信息
     */
    @Schema(description = "返回信息")
    private String message;

    /**
     * 请求用户名
     */
    private String name;

    /**
     * 请求描述
     */
    private String describe;

    /**
     * 请求参数json
     */
    private String json;

    public static Result<String> fail(String code, String message) {
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
