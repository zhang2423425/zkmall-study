package com.shop.zkmall.app.param.rockeMQ;


import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel(description = "传给rocketMQ的参数")
@AllArgsConstructor
@NoArgsConstructor
public class SubmitParam implements Serializable {


    private OrderParam orderParam;

    private CereBuyerUser cereBuyerUser;
    @Schema(description = "ip地址")
    private String ip;
    @Schema(description = "用TimeUtils.yyMMddHHmmss")
    private String time;

}
