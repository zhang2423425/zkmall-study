/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.business.page.order.Product;
import com.shop.zkmall.business.page.order.ShopOrder;
import com.shop.zkmall.business.page.order.ShopOrderExportDTO;
import com.shop.zkmall.business.param.business.BusinessSyncToScrmParam;
import com.shop.zkmall.business.param.order.OrderDileveryParam;
import com.shop.zkmall.business.param.order.OrderGetAllParam;
import com.shop.zkmall.business.param.order.OrderGetByIdParam;
import com.shop.zkmall.business.param.order.UpdateOrderPriceParam;
import com.shop.zkmall.business.service.order.CereOrderDileverService;
import com.shop.zkmall.business.service.order.CereShopOrderService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.business.utils.ScrmSyncVerifyUtil;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.enums.OrderStateEnum;
import com.shop.zkmall.commons.enums.PaymentModeEnum;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单
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

    private final CereOrderDileverService cereOrderDileverService;

    private final ScrmSyncVerifyUtil scrmSyncVerifyUtil;

    /**
     * 订单管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "订单管理查询")
    public Result<Page<ShopOrder>> getAll(@RequestBody OrderGetAllParam param) {
        param.setShopId(ContextUtil.getShopId());
        Page<ShopOrder> page = cereShopOrderService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 订单详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "订单详情查询")
    public Result<ShopOrder> getById(@RequestBody OrderGetByIdParam param) {
        ShopOrder shopOrder = cereShopOrderService.getById(ContextUtil.getShopId(), param.getOrderId());
        return new Result<>(shopOrder);
    }

    /**
     * 发货
     */
    @PostMapping(value = "dilevery")
    @NoRepeatSubmit
    @Operation(summary = "发货")
    @WebRequestLog
    public Result<String> dilevery(@RequestBody OrderDileveryParam param, HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereOrderDileverService.dilevery(param, user);
        return new Result<>(user.getUsername(), "发货", GsonUtil.objectToGson(param));
    }

    /**
     * 订单管理导出
     */
    @PostMapping(value = "export")
    @Operation(summary = "订单管理导出")
    public void export(@RequestBody OrderGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setShopId(ContextUtil.getShopId());
        param.setPage(1);
        param.setPageSize(10000);
        Page<ShopOrder> page = cereShopOrderService.getAll(param);
        //Map<Long, Integer> buyerUserCountMap = new HashMap<>();
        Map<Long, Integer> tmpCountMap = new HashMap<>();
        if (param.getState() != null && param.getState() == 2) {
            Map<Long, Integer> countMap = page.getList().stream().collect(Collectors.groupingBy(ShopOrder::getBuyerUserId, Collectors.collectingAndThen(Collectors.toList(), List::size)));
            tmpCountMap.putAll(countMap);
        } else {
            for (ShopOrder shopOrder : page.getList()) {
                tmpCountMap.put(shopOrder.getBuyerUserId(), 1);
            }
        }
        List<ShopOrderExportDTO> list = page.getList().stream().map(s -> {
            ShopOrderExportDTO shopOrderExportDTO = new ShopOrderExportDTO();
            shopOrderExportDTO.setOrderId(s.getOrderId());
            shopOrderExportDTO.setCreateTime(s.getCreateTime());
            shopOrderExportDTO.setCustomerName(s.getCustomerName());
            shopOrderExportDTO.setNumber(s.getNumber());
            shopOrderExportDTO.setPrice(s.getPrice());
            shopOrderExportDTO.setPaymentTime(s.getPaymentTime());
            shopOrderExportDTO.setReceiveName(s.getReceiveName());
            shopOrderExportDTO.setReceiveAdress(s.getReceiveAdress() + " " + s.getAddress());
            shopOrderExportDTO.setReceivePhone(s.getReceivePhone());
            shopOrderExportDTO.setRemark(s.getRemark());
            shopOrderExportDTO.setTransactionId(s.getTransactionId());
            shopOrderExportDTO.setState(OrderStateEnum.getMsgByCode(s.getState()));
            shopOrderExportDTO.setPaymentMode(PaymentModeEnum.getMsgByCode(s.getPaymentMode()));
            if (EmptyUtils.isNotEmpty(s.getProducts())) {
                //Map<Long, List<Product>> pMap = s.getProducts().stream().collect(Collectors.groupingBy(Product::getProductId));
                StringBuilder builder = new StringBuilder();
                for (Product p : s.getProducts()) {
                    builder.append(p.getProductName()).append(" ").append(p.getNumber()).append(StringUtils.isNotBlank(p.getBrandName()) ? " " + p.getBrandName() : "").append(StringUtils.isNotBlank(p.getSKU()) ? " " + p.getSKU() : "").append("\n");
                }
                //String productNames=s.getProducts().stream().map(Product::getProductName).collect(Collectors.joining("\n"));
                builder.deleteCharAt(builder.length() - 1);
                shopOrderExportDTO.setProductNames(builder.toString());
            }
            if (param.getState() != null && param.getState() == 2) {
                shopOrderExportDTO.setHistoryOrderNum(s.getTotal() - tmpCountMap.get(s.getBuyerUserId()));
                tmpCountMap.put(s.getBuyerUserId(), tmpCountMap.get(s.getBuyerUserId()) - 1);
            } else {
                shopOrderExportDTO.setHistoryOrderNum(s.getTotal() - tmpCountMap.get(s.getBuyerUserId()));
                tmpCountMap.put(s.getBuyerUserId(), tmpCountMap.get(s.getBuyerUserId()) + 1);
            }
            return shopOrderExportDTO;

        }).collect(Collectors.toList());

        //定义导出的excel名字
        String excelName = "订单列表";
        ExcelExportUtils.exportExcel(request, response, excelName, list, ShopOrderExportDTO.class);
    }

    /**
     * 同步订单列表到scrm
     */
    @PostMapping(value = "syncOrderList")
    @Operation(summary = "同步订单列表到scrm")
    public Result<List<ShopOrder>> syncOrderList(@RequestBody BusinessSyncToScrmParam param) {
        if (param == null || param.getSecret() == null || param.getPageSize() == null) {
            return new Result<>(Collections.emptyList());
        }
        scrmSyncVerifyUtil.verify(param.getShopId(), param.getSecret());
        List<ShopOrder> list = cereShopOrderService.syncOrderList(param.getShopId(), param.getLastId(), param.getPageSize());
        return new Result<>(list);
    }

    /**
     * 查询订单详情给scrm
     */
    @PostMapping(value = "getOrderDetailForScrm")
    @Operation(summary = "查询订单详情给scrm")
    public Result<ShopOrder> getOrderDetailForScrm(@RequestBody BusinessSyncToScrmParam param) {
        if (param == null || param.getSecret() == null) {
            return new Result<>(null);
        }
        scrmSyncVerifyUtil.verify(param.getShopId(), param.getSecret());
        ShopOrder shopOrder = cereShopOrderService.getById(param.getShopId(), param.getBusinessId());
        return new Result<>(shopOrder);
    }

    /**
     * 订单改价
     */
    @PostMapping(value = "updateOrderPrice")
    @NoRepeatSubmit
    @Operation(summary = "订单改价")
    @WebRequestLog
    public Result<String> updateOrderPrice(@RequestBody UpdateOrderPriceParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopOrderService.updateOrderPrice(param, user);
        return new Result<>(user.getUsername(), "订单改价", GsonUtil.objectToGson(param));
    }
}
