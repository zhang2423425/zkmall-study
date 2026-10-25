/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.page.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 订单调取微信支付返回数据
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "OrderPay", description = "订单支付返回数据")
public class OrderPay {

    /**
     * appid
     */
    @Schema(description = "appid")
    private String appId;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    private String timeStamp;

    /**
     * nonceStr
     */
    @Schema(description = "nonceStr")
    private String nonceStr;

    /**
     * package
     */
    @Schema(description = "package")
    @JsonProperty("package")
    private String packages;

    /**
     * 签名类型
     */
    @Schema(description = "签名类型")
    private String signType;

    /**
     * 签名
     */
    @Schema(description = "签名")
    private String paySign;

    /**
     * 收款码url
     */
    @Schema(description = "收款码url")
    private String codeUrl;

    /**
     * h5支付链接
     */
    @Schema(description = "h5支付链接")
    private String mwebUrl;

    /**
     * 商户号
     */
    @Schema(description = "商户号")
    private String partnerId;

    /**
     * 支付宝小程序支付-创建的订单id
     */
    @Schema(description = "支付宝小程序支付-创建的订单id")
    private String tradeNo;

    /**
     * 预支付id
     */
    @Schema(description = "预支付id")
    private String prepayId;
}
