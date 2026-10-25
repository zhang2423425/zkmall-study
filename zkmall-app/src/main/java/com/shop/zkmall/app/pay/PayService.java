/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.pay;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface PayService {

    /**
     * 微信支付
     *
     * @param orderFormid  订单编号
     * @param money        订单金额
     * @param openid       客户openid
     * @param ip           服务ip地址
     * @param type         小程序或app
     * @param huabeiPeriod 花呗分期数
     */
    Map<String, String> gotoPay(String orderFormid, BigDecimal money, String openid, String ip, Integer type, Integer huabeiPeriod);

    /**
     * 售后微信退款
     *
     * @param transactionId 支付单号
     * @param outRefundNo   支付生成的退款单号
     * @param total         订单总金额
     * @param refund        退款金额
     */
    Map<String, String> refund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception;

    /**
     * 订单取消微信退款
     *
     * @param transactionId 支付单号
     * @param outRefundNo   支付生成的退款单号
     * @param total         订单总金额
     * @param refund        退款金额
     */
    Map<String, String> orderRefund(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception;

    /**
     * 保证金退款
     *
     * @param transactionId 支付单号
     * @param outRefundNo   支付生成的退款单号
     * @param total         订单总金额
     * @param refund        退款金额
     */
    Map<String, String> refundBond(String transactionId, String outRefundNo, BigDecimal total, BigDecimal refund) throws Exception;


    /**
     * 生成保证金收款码返回
     *
     * @param orderFormid 订单编号
     * @param money       金额
     * @param ip          IP地址
     * @param tradeType   支付类型
     */
    String getCollectionCode(String orderFormid, BigDecimal money, String ip, String tradeType) throws Exception;

    /**
     * 生成订单收款码返回
     *
     * @param orderFormid 订单编号
     * @param money       金额
     * @param ip          IP地址
     * @param tradeType   支付类型
     */
    String getOrderCollectionCode(String orderFormid, BigDecimal money, String ip, String tradeType) throws Exception;

}
