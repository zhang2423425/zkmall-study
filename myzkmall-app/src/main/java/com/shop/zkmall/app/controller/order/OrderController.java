package com.shop.zkmall.app.controller.order;


import com.shop.zkmall.app.page.order.OrderPay;
import com.shop.zkmall.app.page.order.Orders;
import com.shop.zkmall.app.page.order.PayUrl;
import com.shop.zkmall.app.page.settlement.Settlement;
import com.shop.zkmall.app.param.order.OrderGetAllParam;
import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.app.param.order.PayParam;
import com.shop.zkmall.app.param.risk.RiskCheck;
import com.shop.zkmall.app.param.settlement.SettlementParam;
import com.shop.zkmall.app.service.dict.CerePlatformDictService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.commons.config.AlipayConfig;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.HttpUtils;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Slf4j
@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final CereShopOrderService cereShopOrderService;

    private final CerePlatformDictService cerePlatformDictService;

    private final AlipayConfig alipayConfig;


//    @NoRepeatSubmit
//    @WebRequestLog
//    @RiskCheck
    @Operation(summary = "提交订单")
    @PostMapping("submit")
    public Result<PayUrl> placeOrder(@RequestBody OrderParam orderParam, HttpServletRequest request ) throws Exception {


        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String clientIp = HttpUtils.getClientIp(request);


        PayUrl payUrl = cereShopOrderService.submit(orderParam,user,clientIp);

        if ("订单受理中，如果成功，请稍后支付费用".equals(payUrl.getCode())){
            return new Result<>(payUrl,CoReturnFormat.SUCCESS,user.getWechatName(),
                    "订单受理中，如果成功，请稍后支付费用", GsonUtil.objectToGson(orderParam));
        }
        if (payUrl != null) {
            //相当于查询配置
            CerePlatformDict cerePlatformDict = cerePlatformDictService.getByName("auto_pay");
            if(cerePlatformDict!=null&& Objects.equals(cerePlatformDict.getDictDescribe(),"true")){
                PayParam payParam = new PayParam();
                payParam.setType(1);
                payParam.setOrderId(payUrl.getOrderId());
                payParam.setCollageId(payUrl.getCollageId());
                log.info("---------走了controller--pay----------");
                pay(payParam);
            }
        }

        return new Result<>(payUrl,CoReturnFormat.SUCCESS,user.getWechatName(),
                "提交订单", GsonUtil.objectToGson(orderParam));
    }

    @PostMapping("getSettlement")
    @Operation(summary = "结算查询")
    public Result<Settlement> getSettlement(@RequestBody SettlementParam settlementParam,HttpServletRequest request){

        CereBuyerUser user =(CereBuyerUser) request.getAttribute("user");
        Settlement settlement = cereShopOrderService.getSettlement(settlementParam, user);
        return new Result<>(settlement,CoReturnFormat.SUCCESS);
    }

    @PostMapping(value = "pay")
    @Operation(summary = "支付业务处理接口")
    public Result<String> pay(@RequestBody PayParam param) throws Exception {
        cereShopOrderService.pay(param);
        return new Result<>(CoReturnFormat.SUCCESS);
    }

    @PostMapping("gotoH5Pay")
    @NoRepeatSubmit
    @Operation(summary = "H5立即支付")
    @WebRequestLog
    public Result<OrderPay> gotoH5Pay(@RequestBody PayParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
//        OrderPay pay = cereShopOrderService.gotoH5Pay(param, user, ip);
        return new Result<>( CoReturnFormat.SUCCESS, user.getWechatName(), "H5立即支付", GsonUtil.objectToGson(param));
    }

    /**
     * 查询花呗手续费配置
     */
    @GetMapping(value = "getHuabeiConfig")
    @Operation(summary = "查询花呗手续费配置")
    public Result<Map<String, Object>> getHuabeiFeeRateList() {
        Map<String, Object> map = new HashMap<>();
        map.put("huabeiFeeRateList", alipayConfig.getHuabeiFeerateList());
        map.put("huabeiChargeType", alipayConfig.getHuabeiChargeType());
        return new Result<>(map, CoReturnFormat.SUCCESS);
    }
    /**
     * 订单管理查询
     */
    @GetMapping("getAll")
    @Operation(summary = "订单管理查询")
    public Result<Page<Orders>> getAll(OrderGetAllParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Page<Orders> page = cereShopOrderService.getAll(param, user);
        return new Result<>(page, CoReturnFormat.SUCCESS);
    }


}
