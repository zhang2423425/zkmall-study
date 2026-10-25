package com.shop.zkmall.app.redis.listener;

import com.shop.zkmall.app.redis.config.RedisDelayedQueueListener;
import com.shop.zkmall.app.service.redis.CereRedisKeyService;
import com.shop.zkmall.app.utils.OrderUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.dao.redis.AppTaskBodyDTO;
import com.shop.zkmall.commons.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AppDelayQueueConsumeListener /*implements RedisDelayedQueueListener<AppTaskBodyDTO>*/ {

    private final OrderUtil orderUtil;

    private final CereRedisKeyService cereRedisKeyService;
//    @Override
    public void invoke(AppTaskBodyDTO appTaskBodyDTO) {
        String body = appTaskBodyDTO.getBody();
        String code = appTaskBodyDTO.getCode();
        String time = TimeUtils.yyMMddHHmmss();

        try {
            if (StringEnum.ORDER_CONFIRM_DILEVERY.getCode().equals(code)){
                orderUtil.updateOrderState(body,time,IntegerEnum.ORDER_FINISH.getCode());
            }
            cereRedisKeyService.deleteByKey(code+"-"+body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
