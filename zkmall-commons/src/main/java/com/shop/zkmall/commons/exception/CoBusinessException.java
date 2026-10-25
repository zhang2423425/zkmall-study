/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.exception;


import com.shop.zkmall.commons.constant.CoReturnFormat;

/**
 * 业务异常统一处理
 *
 * @author zkmall zkmallvip@163.com
 */
public class CoBusinessException extends BaseException {

    public CoBusinessException(String errorCode) {
        super(errorCode, CoReturnFormat.getMessage(errorCode));
    }

    public CoBusinessException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public CoBusinessException(String errorCode, Object errorData) {
        super(errorCode, CoReturnFormat.getMessage(errorCode), errorData);
    }

    public CoBusinessException(String errorCode, Throwable t) {
        super(errorCode, CoReturnFormat.getMessage(errorCode), t);
    }

    /**
     * 异常堆栈增加错误代码和绑定变量
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("业务异常，异常代码[").append(this.code).append("]\n");
        sb.append("异常信息:[").append(this.message).append("]\n");
        return sb.toString();
    }

}
