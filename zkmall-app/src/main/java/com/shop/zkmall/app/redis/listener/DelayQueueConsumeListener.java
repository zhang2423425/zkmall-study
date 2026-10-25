/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.redis.listener;

import com.shop.zkmall.app.redis.config.RedisDelayedQueueListener;
import com.shop.zkmall.app.redis.dto.TaskBodyDTO;
import com.shop.zkmall.app.service.after.CereOrderAfterService;
import com.shop.zkmall.app.service.notice.CereNoticeService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.app.service.stock.CereStockService;
import com.shop.zkmall.app.utils.OrderUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.after.CereOrderAfter;
import com.shop.zkmall.commons.domain.notice.CereNotice;
import com.shop.zkmall.commons.domain.order.CereShopOrder;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 监听器
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DelayQueueConsumeListener implements RedisDelayedQueueListener<TaskBodyDTO> {

    private final CereRedisKeyService cereRedisKeyService;

    private final CereShopOrderService cereShopOrderService;

    private final CereOrderAfterService cereOrderAfterService;

    private final CereNoticeService cereNoticeService;

    private final CereStockService cereStockService;

    private final OrderUtil orderUtil;

    @Override
    public void invoke(TaskBodyDTO taskBodyDTO) {
        //这里调用你延迟之后的代码
        log.info("invoke redis Listener {} {}", taskBodyDTO.getCode(), taskBodyDTO.getBody());
        String code = taskBodyDTO.getCode();
        String body = taskBodyDTO.getBody();
        String time = TimeUtils.yyMMddHHmmss();
        try {
            if (StringEnum.ORDER_AUTOMATIC_CANCEL.getCode().equals(code)) {
                //修改订单状态为已关闭
                updateOrderClose(body, time);
            } else if (StringEnum.ORDER_CONFIRM_DILEVERY.getCode().equals(code)) {
                //修改订单状态为已完成
                orderUtil.updateOrderState(body, time, IntegerEnum.ORDER_FINISH.getCode());
            } else if (StringEnum.AFTER_CANCEL.getCode().equals(code)) {
                //售后单三天后未处理自动关闭
                updateAfterCancel(body, time, IntegerEnum.AFTER_CANCEL.getCode());
            }
            //删除redis延时任务记录
            cereRedisKeyService.deleteByKey(code + "-" + body);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void updateAfterCancel(String afterId, String time, Integer state) {
        //修改售后单状态为已关闭
        CereOrderAfter cereOrderAfter = new CereOrderAfter();
        cereOrderAfter.setAfterId(Long.parseLong(afterId));
        cereOrderAfter.setAfterState(state);
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterService.update(cereOrderAfter);
    }

    private void updateOrderClose(String id, String time) {
        Long orderId = Long.parseLong(id);
        //更新订单状态为已关闭
        CereShopOrder order = cereShopOrderService.findById(orderId);
        if (IntegerEnum.ORDER_STOP.getCode().equals(order.getState())) {
            return;
        }
        order.setState(IntegerEnum.ORDER_STOP.getCode());
        order.setUpdateTime(time);
        cereShopOrderService.update(order);
        // 库存回流
        cereStockService.rollbackStockByOrderId(orderId);

        //新增订单关闭消息
        CereNotice cereNotice = new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_CANCEL.getCode());
        cereNotice.setNoticeContent("您未在规定时间完成付款，订单" + order.getOrderFormid() + "已关闭,点击查看详情");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }

}