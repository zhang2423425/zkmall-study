/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.order.impl;

import com.shop.zkmall.business.dao.order.CereOrderDileverDAO;
import com.shop.zkmall.business.param.after.AfterIdParam;
import com.shop.zkmall.business.param.order.OrderDileveryParam;
import com.shop.zkmall.business.service.after.CereAfterDileverService;
import com.shop.zkmall.business.service.after.CereOrderAfterService;
import com.shop.zkmall.business.service.log.CerePlatformLogService;
import com.shop.zkmall.business.service.notice.CereNoticeService;
import com.shop.zkmall.business.service.order.CereOrderDileverService;
import com.shop.zkmall.business.service.order.CereShopOrderService;
import com.shop.zkmall.business.utils.RedisUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.after.CereAfterDilever;
import com.shop.zkmall.commons.domain.after.CereOrderDilever;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.TimeUtils;
import com.shop.zkmall.commons.utils.third.Tools;
import com.shop.zkmall.sms.strategy.SmsStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereOrderDileverServiceImpl implements CereOrderDileverService {

    private final CereOrderDileverDAO cereOrderDileverDAO;

    private final CerePlatformLogService cerePlatformLogService;

    private final CereShopOrderService cereShopOrderService;

    private final CereAfterDileverService cereAfterDileverService;

    private final CereNoticeService cereNoticeService;

    private final CereOrderAfterService cereOrderAfterService;

    private final SmsStrategy smsStrategy;

    private final RedisUtil redisUtil;

    @Value("${autoDeliveryTime}")
    private Long autoDeliveryTime;


    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void dilevery(OrderDileveryParam param, CerePlatformBusiness user) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereOrderDilever cereOrderDilever = new CereOrderDilever();
        cereOrderDilever.setOrderId(param.getOrderId());
        cereOrderDilever.setExpress(param.getExpress());
        cereOrderDilever.setDeliverFormid(param.getDeliverFormid());
        cereOrderDilever.setCreateTime(time);
        cereOrderDileverDAO.insert(cereOrderDilever);
        //更新订单状态为待收货
        CereShopOrder cereShopOrder = cereShopOrderService.findById(param.getOrderId());
        cereShopOrder.setOrderId(param.getOrderId());
        cereShopOrder.setState(IntegerEnum.ORDER_HAVE_DILEVERY.getCode());
        cereShopOrderService.updateState(cereShopOrder);
        //新增日志
        cerePlatformLogService.addLog(user, "订单管理", "商户端操作", "发货", cereOrderDilever.getOrderId(), time);
        //新增自动15天确认收货定时任务
//        stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId(),1,15*24*60*60*1000);
//        cereRedisKeyService.add(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+com.shop.zkmall.admin.param.getOrderId(),TimeUtils.getMoreDayAfter(time,15));

        redisUtil.addAppDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId(), TimeUtils.getMoreDayAfter(time, 15));


        long deliveryTime = 7 * 24 * 3600 * 1000L;
        if (autoDeliveryTime != null) {
            deliveryTime = autoDeliveryTime;
        }
        Date endTime = new Date(TimeUtils.parseDate(time).getTime() + deliveryTime);
        //如果订单申请了退款或者退货，并且在流程中，则不做自动确认收货
        List<Integer> afterStateList = cereOrderAfterService.selectAfterStateList(param.getOrderId());
        boolean containAllStateFree = new HashSet<>(IntegerEnum.CONFIRM_DELIVERY_AFTER_STATE_LIST).containsAll(afterStateList);
        if (CollectionUtils.isEmpty(afterStateList) || containAllStateFree) {
//            stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + com.shop.zkmall.admin.param.getOrderId(), 1, deliveryTime);
//            cereRedisKeyService.add(StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + com.shop.zkmall.admin.param.getOrderId(), Tools.date2Str(endTime));
            redisUtil.addAppDelayQueue(StringEnum.ORDER_CONFIRM_DILEVERY.getCode(), param.getOrderId(), Tools.date2Str(endTime));
            log.info("add ORDER_CONFIRM_DILEVERY Redis Message key = {}", StringEnum.ORDER_CONFIRM_DILEVERY.getCode() + "-" + param.getOrderId());
        }

        //新增订单已完成消息
        CereNotice cereNotice = new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(cereShopOrder.getBuyerUserId());
        cereNotice.setShopId(cereShopOrder.getShopId());
        cereNotice.setReceive(3);
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_DELIVERY.getCode());
        cereNotice.setNoticeContent("您购买的" + cereShopOrder.getOrderFormid() + "商家已发货，点击查看物流详情");
        cereNotice.setOnly(cereShopOrder.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);

        //发送短信
        if (StringUtils.isNotBlank(cereShopOrder.getCustomerPhone()) && "15986612770".equals(cereShopOrder.getCustomerPhone())) {
            smsStrategy.sendContent(cereShopOrder.getCustomerPhone(), "您订购的商品已发货，宝贝快马加鞭的奔向您，请注意查收！海觅海淘竭诚为您服务！【海觅SeaMee】");
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundDilevery(AfterIdParam param, CerePlatformBusiness user) {
        String time = TimeUtils.yyMMddHHmmss();
        CereAfterDilever cereAfterDilever = new CereAfterDilever();
        cereAfterDilever.setCreateTime(time);
        cereAfterDilever.setExpress(param.getExpress());
        cereAfterDilever.setDeliverFormid(param.getDeliverFormid());
        cereAfterDilever.setOrderId(param.getOrderId());
        //    cereAfterDilever.setAfterId(com.shop.zkmall.admin.param.getAfterId());
        cereAfterDileverService.insert(cereAfterDilever);
        //更新订单状态为待收货
//        CereShopOrder cereShopOrder=new CereShopOrder();
//        cereShopOrder.setOrderId(com.shop.zkmall.admin.param.getOrderId());
//        cereShopOrder.setState(IntegerEnum.ORDER_HAVE_DILEVERY.getCode());
//        cereShopOrderService.updateState(cereShopOrder);
        //新增日志
        cerePlatformLogService.addLog(user, "售后管理", "商户端操作", "仅退款发货", param.getAfterId(), time);
    }

}
