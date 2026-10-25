/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.utils;

import com.shop.zkmall.app.service.after.CereOrderAfterService;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderUtil {

    private final CereShopOrderService cereShopOrderService;

    private final CereOrderAfterService cereOrderAfterService;

    private final CereNoticeService cereNoticeService;

    public void updateOrderState(String id, String time, Integer state) {
        //更新订单状态为已完成
        CereShopOrder order = cereShopOrderService.findById(Long.parseLong(id));
        if (!IntegerEnum.ORDER_HAVE_DILEVERY.getCode().equals(order.getState())) {
            //如果不是待收货不做处理
            return;
        }
        //如果售后状态不满足，则不做自动确认收货
        List<Integer> afterStateList = cereOrderAfterService.selectAfterStateList(order.getId());
        boolean containAllStateFree = new HashSet<>(IntegerEnum.CONFIRM_DELIVERY_AFTER_STATE_LIST).containsAll(afterStateList);
        if (!CollectionUtils.isEmpty(afterStateList) && !containAllStateFree) {
            return;
        }
        order.setState(state);
        order.setUpdateTime(time);
        order.setReceiveTime(time);
        cereShopOrderService.update(order);
        //新增订单已完成消息
        CereNotice cereNotice = new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_FINISH.getCode());
        cereNotice.setNoticeContent("订单" + order.getOrderFormid() + "已确认收货，期待您分享商品评价和购物心得");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }

}
