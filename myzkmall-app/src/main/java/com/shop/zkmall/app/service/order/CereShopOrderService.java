/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.order;

import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.*;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.settlement.Distribution;
import com.shop.zkmall.app.page.settlement.Settlement;
import com.shop.zkmall.app.param.comment.CommentSaveParam;
import com.shop.zkmall.app.param.order.OrderGetAllParam;
import com.shop.zkmall.app.param.order.OrderGetByIdParam;
import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.app.param.order.PayParam;
import com.shop.zkmall.app.param.settlement.SettlementParam;
import com.shop.zkmall.commons.domain.after.CereAfterDilever;
import com.shop.zkmall.commons.domain.buyer.CereBuyerReceive;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;
import com.shop.zkmall.commons.domain.order.CereShopOrder;

import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopOrderService {

    Settlement getSettlement(SettlementParam param, CereBuyerUser user);

    PayUrl submit(OrderParam param, CereBuyerUser user, String ip) throws Exception;

    OrderPay gotoPay(PayParam param, CereBuyerUser user, String ip) throws Exception;

    void handleWxLog(String orderFormId, String transactionId, String orderNo) throws Exception;

    Page<Orders> getAll(OrderGetAllParam param, CereBuyerUser user) throws Exception;

    OrderDetail getById(OrderGetByIdParam param) throws Exception;

    void confirm(OrderGetByIdParam param, CereBuyerUser user);

    void update(CereShopOrder cereShopOrder);

    void cancel(OrderGetByIdParam param, CereBuyerUser user) throws Exception;

    void addComment(CommentSaveParam param, CereBuyerUser user);

    OrderPay gotoAppPay(PayParam param, CereBuyerUser user, String ip) throws Exception;

    OrderPay gotoH5Pay(PayParam param, CereBuyerUser user, String ip) throws Exception;

    void addToComment(CommentSaveParam param, CereBuyerUser user);

    void delete(OrderGetByIdParam param, CereBuyerUser user);

    void returnExpress(CereAfterDilever dilever, CereBuyerUser user);

    List<Dilevery> getDilevery(DileveryParam param) throws Exception;

    void pay(PayParam param) throws Exception;

    PayUrl getUrl(OrderGetByIdParam param, CereBuyerUser user, String ip) throws Exception;

    PayUrl checkPay(PayParam param);

    List<CartSku> refund(OrderGetByIdParam param, CereBuyerUser user);

    void handleRefundWxLog(String orderFormId, String transactionId, String orderNo) throws Exception;

    Distribution setLogisticPrice(List<CereOrderLogistics> logistics, List<CartSku> skus, CereBuyerReceive receive, Map<Long, Integer> map);

    CereShopOrder findById(Long orderId);

    void updateBuyerData(Long buyerUserId, Long id);

    Long getWaitPayNumByHours(Integer hours, Long buyerUserId);

    Long getOrderNumByHours(Integer hours, Long buyerUserId);

    /**
     * 查询付款人数，如果skuId不传，则只查询商品级别
     */
    Integer findPayUsers(Long productId, Long skuId);

    /**
     * 查询某个商品1小时内的下单
     */
    List<BroadCastDTO> findRecentOrder(String oneHourAgo, Long productId);

    String gotoAliPcPay(PayParam param, CereBuyerUser user, String ip);

    String gotoAliCodePay(PayParam param, CereBuyerUser user, String ip);

}
