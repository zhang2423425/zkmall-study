package com.shop.zkmall.app.service.rocketMQ;

import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.app.param.rockeMQ.SubmitParam;
import com.shop.zkmall.app.service.order.placeOrderTemplate.PlaceOrderFactory;
import com.shop.zkmall.app.service.order.placeOrderTemplate.PlaceOrderTemplate;
import com.shop.zkmall.app.utils.SystemConstants;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.exception.CoBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@RocketMQMessageListener(topic = SystemConstants.TOPIC_SUBMIT_HOT_SKU,consumerGroup = SystemConstants.GROUP_HOTSKU)
@Service
@RequiredArgsConstructor
public class HotSkuOrderService implements RocketMQListener<SubmitParam>  {

    private final PlaceOrderFactory placeOrderFactory;
    @Override
    public void onMessage(SubmitParam submitParam) {
        OrderParam orderParam = submitParam.getOrderParam();
        String ip = submitParam.getIp();
        String time = submitParam.getTime();
        CereBuyerUser cereBuyerUser = submitParam.getCereBuyerUser();
        Integer type = orderParam.getType();
        PlaceOrderTemplate placeOrderTemplate = placeOrderFactory.getPlaceOrderTemplate(type);
        try {
            log.info("--------------MQservice收到了订单，准备下单--------------");
            placeOrderTemplate.placeOrder(orderParam,cereBuyerUser,ip,time);
        }catch (CoBusinessException e){
            log.warn("商品不可售，跳过该消息，不再重试");
            //不抛异常！直接结束
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
