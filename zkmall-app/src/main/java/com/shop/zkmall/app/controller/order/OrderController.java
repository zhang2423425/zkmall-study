/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.order.Dilevery;
import com.shop.zkmall.app.page.order.DileveryParam;
import com.shop.zkmall.app.page.order.OrderDetail;
import com.shop.zkmall.app.page.order.OrderPay;
import com.shop.zkmall.app.page.order.Orders;
import com.shop.zkmall.app.page.order.PayUrl;
import com.shop.zkmall.app.page.settlement.Settlement;
import com.shop.zkmall.app.param.comment.CommentSaveParam;
import com.shop.zkmall.app.param.order.OrderGetAllParam;
import com.shop.zkmall.app.param.order.OrderGetByIdParam;
import com.shop.zkmall.app.param.order.OrderParam;
import com.shop.zkmall.app.param.order.PayParam;
import com.shop.zkmall.app.param.risk.RiskCheck;
import com.shop.zkmall.app.param.settlement.SettlementParam;
import com.shop.zkmall.app.service.dict.CerePlatformDictService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.commons.config.AlipayConfig;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.constant.WxPayEnum;
import com.shop.zkmall.commons.domain.after.CereAfterDilever;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.GsonUtil;
import com.shop.zkmall.commons.utils.HttpUtils;
import com.shop.zkmall.commons.utils.PayUtil;
import com.shop.zkmall.commons.utils.third.HttpKit;
import com.shop.zkmall.commons.utils.third.PaymentKit;
import com.shop.zkmall.commons.utils.third.WXPayUtil;
import com.shop.zkmall.commons.utils.third.WXPayV3Util;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 订单模块
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Tag(name = "订单模块", description = "订单模块")
public class OrderController {

    private final CereShopOrderService cereShopOrderService;

    private final CerePlatformDictService cerePlatformDictService;

    private final WXPayV3Util wxPayV3Util;

    private final AlipayConfig alipayConfig;

    /**
     * 商户秘钥
     */
    @Value("${weixin.key}")
    private String key;

    /**
     * 结算查询
     *
     * @param param /
     * @return Settlement
     */
    @PostMapping("getSettlement")
    @Operation(summary = "结算查询")
    public Result<Settlement> getSettlement(@RequestBody SettlementParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        Settlement settlement = cereShopOrderService.getSettlement(param, user);
        return new Result<>(settlement, CoReturnFormat.SUCCESS);
    }

    /**
     * 提交订单
     *
     * @param param /
     * @return /
     */
    @PostMapping("submit")
    @NoRepeatSubmit
    @Operation(summary = "提交订单")
    @WebRequestLog
    @RiskCheck
    public Result<PayUrl> submit(@RequestBody OrderParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        PayUrl payUrl = cereShopOrderService.submit(param, user, ip);
        if (payUrl != null) {
            CerePlatformDict cereplatformDict = cerePlatformDictService.getByName("auto_pay");
            if (cereplatformDict != null && Objects.equals(cereplatformDict.getDictDescribe(), "true")) {
                PayParam payParam = new PayParam();
                payParam.setType(1);
                payParam.setOrderId(payUrl.getOrderId());
                payParam.setCollageId(payUrl.getCollageId());
                pay(payParam);
            }
        }
        return new Result<>(payUrl, CoReturnFormat.SUCCESS, user.getWechatName(), "提交订单", GsonUtil.objectToGson(param));
    }

    /**
     * 根据订单id获取收款码
     * 继续付款 调用该接口
     *
     * @param param /
     * @return /
     */
    @GetMapping("getUrl")
    @NoRepeatSubmit
    @Operation(summary = "根据订单id获取收款码")
    public Result<PayUrl> getUrl(OrderGetByIdParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        PayUrl payUrl = cereShopOrderService.getUrl(param, user, ip);
        return new Result<>(payUrl, CoReturnFormat.SUCCESS);
    }

    /**
     * 校验当前订单是否支付成功
     */
    @GetMapping("checkPay")
    @NoRepeatSubmit
    @Operation(summary = "校验当前订单是否支付成功")
    public Result<PayUrl> checkPay(PayParam param) {
        PayUrl payUrl = cereShopOrderService.checkPay(param);
        return new Result<>(payUrl, CoReturnFormat.SUCCESS);
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

    /**
     * 订单详情查询
     */
    @GetMapping("getById")
    @Operation(summary = "订单详情查询")
    public Result<OrderDetail> getById(OrderGetByIdParam param) throws Exception {
        OrderDetail detail = cereShopOrderService.getById(param);
        return new Result<>(detail, CoReturnFormat.SUCCESS);
    }

    /**
     * 小程序立即支付
     */
    @PostMapping("gotoPay")
    @NoRepeatSubmit
    @Operation(summary = "小程序立即支付")
    @WebRequestLog
    public Result<OrderPay> gotoPay(@RequestBody PayParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        OrderPay pay = cereShopOrderService.gotoPay(param, user, ip);
        return new Result<>(pay, CoReturnFormat.SUCCESS, user.getWechatName(), "小程序立即支付", GsonUtil.objectToGson(param));
    }

    /**
     * 填写退货物流单
     */
    @PostMapping("returnExpress")
    @NoRepeatSubmit
    @Operation(summary = "填写退货物流单")
    @WebRequestLog
    public Result<String> returnExpress(@RequestBody CereAfterDilever dilever, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopOrderService.returnExpress(dilever, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "填写退货物流单", GsonUtil.objectToGson(dilever));
    }

    /**
     * 选择物流公司查询
     */
    @GetMapping("getExpressSelect")
    @Operation(summary = "选择物流公司查询")
    public Result<List<CerePlatformDict>> getExpressSelect() {
        List<CerePlatformDict> list = cerePlatformDictService.getExpressSelect();
        return new Result<>(list, CoReturnFormat.SUCCESS);
    }

    /**
     * APP立即支付
     */
    @PostMapping("gotoAppPay")
    @NoRepeatSubmit
    @Operation(summary = "APP立即支付")
    @WebRequestLog
    public Result<OrderPay> gotoAppPay(@RequestBody PayParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        OrderPay pay = cereShopOrderService.gotoAppPay(param, user, ip);

        return new Result<>(pay, CoReturnFormat.SUCCESS, user.getWechatName(), "APP立即支付", GsonUtil.objectToGson(param));
    }

    /**
     * H5立即支付
     */
    @PostMapping("gotoH5Pay")
    @NoRepeatSubmit
    @Operation(summary = "H5立即支付")
    @WebRequestLog
    public Result<OrderPay> gotoH5Pay(@RequestBody PayParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        OrderPay pay = cereShopOrderService.gotoH5Pay(param, user, ip);
        return new Result<>(pay, CoReturnFormat.SUCCESS, user.getWechatName(), "H5立即支付", GsonUtil.objectToGson(param));
    }

    /**
     * 确认收货
     */
    @RequestMapping(value = "confirm", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "确认收货")
    @WebRequestLog
    public Result<String> confirm(@RequestBody OrderGetByIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopOrderService.confirm(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "确认收货", GsonUtil.objectToGson(param));
    }

    /**
     * 取消订单
     */
    @RequestMapping(value = "cancel", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "取消订单")
    @WebRequestLog
    public Result<String> cancel(@RequestBody OrderGetByIdParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopOrderService.cancel(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "取消订单", GsonUtil.objectToGson(param));
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "删除订单")
    @WebRequestLog
    public Result<String> delete(@RequestBody OrderGetByIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopOrderService.delete(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "删除订单", GsonUtil.objectToGson(param));
    }

    /**
     * 添加评论
     */
    @PostMapping("addComment")
    @NoRepeatSubmit
    @Operation(summary = "添加评论")
    @WebRequestLog
    public Result<String> addComment(@RequestBody CommentSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopOrderService.addComment(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "添加评论", GsonUtil.objectToGson(param));
    }

    /**
     * 追加评论
     */
    @RequestMapping(value = "addToComment", method = {RequestMethod.PUT, RequestMethod.POST})
    @NoRepeatSubmit
    @Operation(summary = "追加评论")
    @WebRequestLog
    public Result<String> addToComment(@RequestBody CommentSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        cereShopOrderService.addToComment(param, user);
        return new Result<>(CoReturnFormat.SUCCESS, user.getWechatName(), "追加评论", GsonUtil.objectToGson(param));
    }

    /**
     * 申请退款
     */
    @GetMapping("refund")
    @NoRepeatSubmit
    @Operation(summary = "申请退款")
    @WebRequestLog
    public Result<List<CartSku>> refund(OrderGetByIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        List<CartSku> list = cereShopOrderService.refund(param, user);
        return new Result<>(list, CoReturnFormat.SUCCESS, user.getWechatName(), "申请退款", GsonUtil.objectToGson(param));
    }

    /**
     * 微信支付回调
     */
    @PostMapping("pay/rolBack")
    public void wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("进入微信小程序支付回调");
        String xmlMsg = HttpKit.readData(request);
        log.info("微信小程序通知信息{}", xmlMsg);
        Map<String, String> resultMap = PaymentKit.xmlToMap(xmlMsg);
        if (!EmptyUtils.isEmpty(resultMap)) {
            if (resultMap.get(StringEnum.WX_RETURN_CODE.getCode()).equals(StringEnum.WX_PAY_SUCCESS.getCode())) {
                String orderNo = resultMap.get("out_trade_no");
                log.info("回调out_trade_no={} resultMap={}", orderNo, JSON.toJSONString(resultMap));
                //验证签名
                if (WXPayUtil.isSignatureValid(resultMap, key)) {
                    log.info("回调out_trade_no={}", orderNo);
                    log.info("微信小程序支付回调成功");
                    //截取订单编号
                    String[] split = orderNo.split("-");
                    if (!EmptyUtils.isEmpty(split)) {
                        String orderFormId = split[0];
                        if (!EmptyUtils.isEmpty(orderFormId)) {
                            //处理支付成功后的其他逻辑
                            cereShopOrderService.handleWxLog(orderFormId, resultMap.get("transaction_id"), orderNo);
                        }

                    }
                } else {
                    log.info("signature not valid");
                }
            }
        } else {
            log.info("map为null");
        }
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response.getWriter().write(result);
    }

    /**
     * 微信V3支付或退款回调
     */
    @PostMapping("pay/v3RolBack")
    public void wxProV3Notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("v3RolBack 进入微信appV3回调");
        String body = HttpKit.readPureBodyData(request);
        log.info("v3RolBack appV3通知信息 {}", body);
        JSONObject retObj = JSON.parseObject(body);
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        String nonce = request.getHeader("Wechatpay-Nonce");
        String sign = request.getHeader("Wechatpay-Signature");
        if (!EmptyUtils.isEmpty(retObj) && !EmptyUtils.isEmpty(timestamp)
                && !EmptyUtils.isEmpty(nonce) && !EmptyUtils.isEmpty(sign)) {
            String eventType = retObj.getString("event_type");
            String summary = retObj.getString("summary");
            String decryptedText = wxPayV3Util.decrypt(timestamp, nonce, sign, body);
            log.info("v3RolBack decryptedText {}", decryptedText);
            switch (eventType) {
                case "TRANSACTION.SUCCESS":
                    try {
                        if (StringUtils.isNotBlank(decryptedText)) {
                            JSONObject resultObj = JSON.parseObject(decryptedText);
                            String tradeState = resultObj.getString("trade_state");
                            if (WxPayEnum.PAY_SUCCESS.getCode().equals(tradeState)) {
                                String transactionId = resultObj.getString("transaction_id");
                                String outTradeNo = resultObj.getString("out_trade_no");
                                String[] split = outTradeNo.split("-");
                                if (!EmptyUtils.isEmpty(split)) {
                                    String orderFormId = split[0];
                                    if (!EmptyUtils.isEmpty(orderFormId)) {
                                        //处理支付成功后的其他逻辑
                                        cereShopOrderService.handleWxLog(orderFormId, transactionId, outTradeNo);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("v3RolBack TRANSACTION.SUCCESS decrypt fail {}", e.getMessage(), e);
                        response.setStatus(500);
                        Map<String, String> map = new HashMap<>();
                        map.put("code", "FAIL");
                        map.put("message", "解密失败");
                        response.getWriter().write(JSON.toJSONString(map));
                    }
                    break;
                // 退款回调
                case "REFUND.SUCCESS":
                    try {
                        if (StringUtils.isNotBlank(decryptedText)) {
                            JSONObject resultObj = JSON.parseObject(decryptedText);
                            String refundStatus = resultObj.getString("refund_status");
                            String outTradeNo = resultObj.getString("out_trade_no");
                            String transactionId = resultObj.getString("transaction_id");
                            if (WxPayEnum.REFUND_OK.getCode().equals(refundStatus)) {
                                String[] split = outTradeNo.split("-");
                                if (!EmptyUtils.isEmpty(split)) {
                                    String orderFormId = split[0];
                                    if (!EmptyUtils.isEmpty(orderFormId)) {
                                        cereShopOrderService.handleRefundWxLog(orderFormId, transactionId, outTradeNo);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("v3RolBack REFUND.SUCCESS decrypt fail {}", e.getMessage(), e);
                        response.setStatus(500);
                        Map<String, String> map = new HashMap<>();
                        map.put("code", "FAIL");
                        map.put("message", "解密失败");
                        response.getWriter().write(JSON.toJSONString(map));
                    }
                    break;
            }
        } else {
            log.info("map为null");
        }
    }

    /**
     * 微信退款回调
     */
    @PostMapping("refund/rolBack")
    public void wxRefundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("进入微信小程序退款回调");
        String xmlMsg = HttpKit.readData(request);
        log.info("微信小程序通知信息{}", xmlMsg);
        Map<String, String> resultMap = PaymentKit.xmlToMap(xmlMsg);
        if (!EmptyUtils.isEmpty(resultMap)) {
            if (resultMap.get(StringEnum.WX_RETURN_CODE.getCode()).equals(StringEnum.WX_PAY_SUCCESS.getCode())) {
                //获取微信返回数据进行解密
                String reqInfo = resultMap.get("req_info");
                String str = EmptyUtils.decryption(reqInfo, key);
                Map<String, String> map = PaymentKit.xmlToMap(str);
                String orderNo = map.get("out_trade_no");
                log.info("回调out_trade_no={} resultMap={}", orderNo, JSON.toJSONString(resultMap));
                //if(WXPayUtil.isSignatureValid(resultMap,key)){
                //验证签名
                log.info("回调out_trade_no={}", orderNo);
                log.info("微信小程序退款回调成功");
                //截取订单编号
                String[] split = orderNo.split("-");
                if (!EmptyUtils.isEmpty(split)) {
                    String orderFormId = split[0];
                    if (!EmptyUtils.isEmpty(orderFormId)) {
                        cereShopOrderService.handleRefundWxLog(orderFormId, map.get("transaction_id"), orderNo);
                    }
                }
                //}
            }
        } else {
            log.info("map为null");
        }
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response.getWriter().write(result);
    }

    /**
     * 支付宝小程序支付回调
     */
    @PostMapping("alipay/rolBack")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request) {
        log.info("进入支付宝小程序支付回调");
        Map<String, String> params = PayUtil.getAlipayResultParams(request);
        log.info("支付宝小程序回调信息:{}", params);

        try {
            if (!EmptyUtils.isEmpty(params) && PayUtil.signAlipayVerified(params, alipayConfig.getAlipayPublicKey())) {
                String tradeStatus = params.get(StringEnum.ALI_PAY_TRADE_STATUS.getCode());
                if (StringEnum.ALI_PAY_SUCCESS.getCode().equals(tradeStatus) || StringEnum.ALI_PAY_FINISHED.getCode().equals(tradeStatus)) {
                    String refundFee = params.get("refund_fee");
                    //由于支付宝部分退款也走了 支付的回调接口，所以做此处理
                    if (StringUtils.isNotBlank(refundFee)) {
                        //售后审核通过的退款，暂时由商家端处理 支付宝退款回调
                        //客户端取消订单发起退款，不是部分退款，所以不会回调到这里
                    } else {
                        String orderNo = params.get("out_trade_no");
                        //处理支付成功后的其他逻辑
                        payNotifyInner(orderNo, params.get("trade_no"));
                    }
                    return "success";
                } else {
                    log.error("交易状态异常");
                }
            } else {
                log.error("map为null 或 签名不通过");
            }
        } catch (Exception e) {
            log.error("alipayNotify failed: params = {}", params, e);
        }
        return "failure";
    }

    /**
     * 支付回调业务逻辑
     */
    private void payNotifyInner(String orderNo, String transactionId) throws Exception {
        log.info("支付宝支付回调out_trade_no={}", orderNo);
        //截取订单编号
        String[] split = orderNo.split("-");
        if (!EmptyUtils.isEmpty(split)) {
            String orderFormId = split[0];
            if (!EmptyUtils.isEmpty(orderFormId)) {
                cereShopOrderService.handleWxLog(orderFormId, transactionId, orderNo);
            }
        }
    }

    /**
     * 暂时废弃-支付宝部分退款回调走了 支付的回调地址
     * 支付宝退款回调
     */
    @PostMapping("alipayRefund/rolBack")
    @Deprecated
    public String alipayRefundNotify(HttpServletRequest request) throws Exception {
        log.info("进入支付宝退款回调");
        Map<String, String> params = PayUtil.getAlipayResultParams(request);
        log.info("支付宝退款回调信息:{}", params);

        if (!EmptyUtils.isEmpty(params) && PayUtil.signAlipayVerified(params, alipayConfig.getAlipayPublicKey())) {
            String orderNo = params.get("out_trade_no");
            log.info("支付宝退款回调out_trade_no={}", orderNo);
        } else {
            log.info("map为null 或 签名不通过");
            return "failure";
        }
        return "success";
    }

    /**
     * 物流信息查询
     */
    @GetMapping("getDilevery")
    @Operation(summary = "物流信息查询")
    public Result<List<Dilevery>> getDilevery(DileveryParam param) throws Exception {
        if (StringUtils.isBlank(param.getExpress()) || StringUtils.isBlank(param.getDeliverFormid())) {
            return new Result<>(new ArrayList<>(), CoReturnFormat.PARAM_INVALID);
        }
        List<Dilevery> dileveries = cereShopOrderService.getDilevery(param);
        return new Result<>(dileveries, CoReturnFormat.SUCCESS);
    }

    /**
     * 支付业务处理接口
     */
    @PostMapping(value = "pay")
    @Operation(summary = "支付业务处理接口")
    public Result<String> pay(@RequestBody PayParam param) throws Exception {
        cereShopOrderService.pay(param);
        return new Result<>(CoReturnFormat.SUCCESS);
    }

    /**
     * 支付业务处理接口
     */
    @GetMapping(value = "payOrder")
    @Operation(summary = "支付业务处理接口")
    public Result<String> payOrder(PayParam param) throws Exception {
        cereShopOrderService.pay(param);
        return new Result<>(CoReturnFormat.SUCCESS);
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
     * 支付宝跳转支付
     */
    @PostMapping("gotoAliPcPay")
    @NoRepeatSubmit
    @Operation(summary = "支付宝跳转支付")
    @WebRequestLog
    public Result<String> gotoAliPcPay(@RequestBody PayParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        String payForm = cereShopOrderService.gotoAliPcPay(param, user, ip);
        //HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //response.addHeader("Access-Control-Allow-Origin","*");
        return new Result<>(payForm, CoReturnFormat.SUCCESS, user.getWechatName(), "支付宝跳转支付", GsonUtil.objectToGson(param));
    }

    /**
     * 支付宝跳转支付
     */
    @PostMapping("gotoAliCodePay")
    @NoRepeatSubmit
    @Operation(summary = "返回支付宝支付二维码")
    @WebRequestLog
    public Result<String> gotoAliCodePay(@RequestBody PayParam param, HttpServletRequest request) {
        //获取当前登录账户
        CereBuyerUser user = (CereBuyerUser) request.getAttribute("user");
        String ip = HttpUtils.getClientIp(request);
        String payForm = cereShopOrderService.gotoAliCodePay(param, user, ip);
        return new Result<>(payForm, CoReturnFormat.SUCCESS, user.getWechatName(), "支付宝跳转支付", GsonUtil.objectToGson(param));
    }
}
