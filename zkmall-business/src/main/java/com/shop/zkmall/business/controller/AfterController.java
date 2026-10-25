/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.commons.utils.third.HttpKit;
import com.shop.zkmall.commons.utils.third.PaymentKit;
import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.after.After;
import com.shop.zkmall.business.page.after.AfterExportDTO;
import com.shop.zkmall.business.param.after.AfterGetAllParam;
import com.shop.zkmall.business.param.after.AfterGetByIdParam;
import com.shop.zkmall.business.param.after.AfterIdParam;
import com.shop.zkmall.business.service.after.CereOrderAfterService;
import com.shop.zkmall.business.service.order.CereOrderDileverService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.enums.AfterTypeEnum;
import com.shop.zkmall.commons.enums.OrderStateEnum;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 售后
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("after")
@RequiredArgsConstructor
@Tag(name = "售后模块", description = "售后模块")
public class AfterController {

    private final CereOrderAfterService cereOrderAfterService;

    private final CereOrderDileverService cereOrderDileverService;

    /**
     * 商户秘钥
     */
    @Value("${weixin.key}")
    private String key;

    /**
     * 售后管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "售后管理查询")
    public Result<Page<After>> getAll(@RequestBody AfterGetAllParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<After> page = cereOrderAfterService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 售后详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "售后详情查询")
    public Result<After> getById(@RequestBody AfterGetByIdParam param) {
        After after = cereOrderAfterService.getById(param.getAfterId());
        return new Result<>(after);
    }

    /**
     * 仅退款同意退款申请
     */
    @PostMapping(value = "refundSuccess")
    @NoRepeatSubmit
    @Operation(summary = "仅退款同意退款申请")
    @WebRequestLog
    public Result<String> refundSuccess(@RequestBody AfterIdParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderAfterService.refundSuccess(param, user);
        return new Result<>(user.getUsername(), "仅退款同意退款申请", GsonUtil.objectToGson(param));
    }

    /**
     * 仅退款拒绝退款申请
     */
    @PostMapping(value = "refundRefuse")
    @NoRepeatSubmit
    @Operation(summary = "仅退款拒绝退款申请")
    @WebRequestLog
    public Result<String> refundRefuse(@RequestBody AfterIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderAfterService.refundRefuse(param, user);
        return new Result<>(user.getUsername(), "仅退款拒绝退款申请", GsonUtil.objectToGson(param));
    }

    /**
     * 仅退款发货
     */
    @PostMapping(value = "refundDilevery")
    @NoRepeatSubmit
    @Operation(summary = "仅退款发货")
    @WebRequestLog
    public Result<String> dilevery(@RequestBody AfterIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderDileverService.refundDilevery(param, user);
        return new Result<>(user.getUsername(), "仅退款发货", GsonUtil.objectToGson(param));
    }

    /**
     * 退货退款同意
     */
    @PostMapping(value = "success")
    @NoRepeatSubmit
    @Operation(summary = "退货退款同意")
    @WebRequestLog
    public Result<String> success(@RequestBody AfterIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderAfterService.success(param, user);
        return new Result<>(user.getUsername(), "退货退款同意", GsonUtil.objectToGson(param));
    }

    /**
     * 退货退款拒绝
     */
    @PostMapping(value = "refuse")
    @NoRepeatSubmit
    @Operation(summary = "退货退款拒绝")
    @WebRequestLog
    public Result<String> refuse(@RequestBody AfterIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderAfterService.refuse(param, user);
        return new Result<>(user.getUsername(), "退货退款拒绝", GsonUtil.objectToGson(param));
    }

    /**
     * 退货退款确认收货且退款
     */
    @PostMapping(value = "confirmAndRefund")
    @NoRepeatSubmit
    @Operation(summary = "退货退款确认收货且退款")
    @WebRequestLog
    public Result<String> confirmAndRefund(@RequestBody AfterIdParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderAfterService.confirmAndRefund(param, user);
        return new Result<>(user.getUsername(), "退货退款确认收货且退款", GsonUtil.objectToGson(param));
    }

    /**
     * 退货退款货物有损拒绝退款
     */
    @PostMapping(value = "damaging")
    @NoRepeatSubmit
    @Operation(summary = "退货退款货物有损拒绝退款")
    @WebRequestLog
    public Result<String> damaging(@RequestBody AfterIdParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderAfterService.damaging(param, user);
        return new Result<>(user.getUsername(), "退货退款货物有损拒绝退款", GsonUtil.objectToGson(param));
    }

    /**
     * 退款回调
     */
    @PostMapping(value = {"pay/rolBack"})
    public void wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("进入售后退款回调");
        System.out.println("进入售后退款回调");
        String xmlMsg = HttpKit.readData(request);
        log.info("微信小程序通知信息{}", xmlMsg);
        System.out.println("微信小程序通知信息" + xmlMsg);
        Map<String, String> resultMap = PaymentKit.xmlToMap(xmlMsg);
        if (!EmptyUtils.isEmpty(resultMap)) {
            //获取微信返回数据进行解密
            String reqInfo = resultMap.get("req_info");
            String str = EmptyUtils.decryption(reqInfo, key);
            log.info("解密后数据:{}", str);
            Map<String, String> map = PaymentKit.xmlToMap(str);
            String orderNo = map.get("out_trade_no");
            log.info("回调out_trade_no={}", orderNo);
            if (resultMap.get(StringEnum.WX_RETURN_CODE.getCode()).equals(StringEnum.WX_PAY_SUCCESS.getCode())) {
                log.info("售后退款回调成功");
                cereOrderAfterService.handleRefundSuc(map.get("out_refund_no"), map.get("transaction_id"), orderNo, IntegerEnum.ORDER_PAY_WX.getCode());
            } else {
                //退款失败,修改售后单状态为退款失败
                cereOrderAfterService.refundError(map.get("out_refund_no"));
            }
        } else {
            log.info("map为null");
        }
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response.getWriter().write(result);
    }

    /**
     * 售后管理导出
     */
    @PostMapping(value = "export")
    @Operation(summary = "售后管理导出")
    public void export(@RequestBody AfterGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setShopId(ContextUtil.getShopId());
        Page<After> page = cereOrderAfterService.getAll(param);
        List<AfterExportDTO> list = page.getList().stream().map(s -> {
            AfterExportDTO afterExportDTO = new AfterExportDTO();
            afterExportDTO.setAfterId(s.getAfterId());
            afterExportDTO.setAfterType(AfterTypeEnum.getMsgByCode(s.getAfterType()));
            afterExportDTO.setAfterFormid(s.getAfterFormid());
            afterExportDTO.setAfterProductNames(s.getAfterProductNames());
            afterExportDTO.setCustomerName(s.getCustomerName());
            afterExportDTO.setOrderId(s.getOrderId());
            afterExportDTO.setNumber(s.getNumber());
            afterExportDTO.setAfterState(s.getAfterStateName());
            afterExportDTO.setApplyTime(s.getCreateTime());
            afterExportDTO.setLastAfterTime(s.getLastAfterTime());
            afterExportDTO.setOrderState(OrderStateEnum.getMsgByCode(s.getOrderState()));
            afterExportDTO.setRefundMoney(s.getRefundMoney());
            afterExportDTO.setRemark(s.getRemark());
            return afterExportDTO;
        }).collect(Collectors.toList());
        //定义导出的excel名字
        String excelName = "售后订单列表";
        ExcelExportUtils.exportExcel(request, response, excelName, list, AfterExportDTO.class);
    }
}
