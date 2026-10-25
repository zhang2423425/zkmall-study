/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.admin.page.order.ShopOrder;
import com.shop.zkmall.admin.page.order.ShopOrderExportDTO;
import com.shop.zkmall.admin.page.product.Product;
import com.shop.zkmall.admin.param.order.OrderGetAllParam;
import com.shop.zkmall.admin.param.order.OrderGetByIdParam;
import com.shop.zkmall.admin.service.order.CereShopOrderService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.enums.OrderStateEnum;
import com.shop.zkmall.commons.enums.PaymentModeEnum;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Tag(name = "订单管理", description = "订单管理")
public class OrderController {

    private final CereShopOrderService cereShopOrderService;

    /**
     * 订单管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "订单管理查询")
    public Result<Page<ShopOrder>> getAll(@RequestBody OrderGetAllParam param) {
        Page<ShopOrder> page = cereShopOrderService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 订单详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "订单详情查询")
    public Result<ShopOrder> getById(@RequestBody OrderGetByIdParam param) {
        ShopOrder shopOrder = cereShopOrderService.getById(param.getOrderId());
        return new Result<>(shopOrder);
    }

    /**
     * 订单管理导出
     */
    @PostMapping(value = "export")
    @Operation(summary = "订单管理导出")
    public void export(@RequestBody OrderGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setPage(1);
        param.setPageSize(10000);
        Page<ShopOrder> page = cereShopOrderService.getAll(param);
        List<ShopOrderExportDTO> list = page.getList().stream().map(s -> {
            ShopOrderExportDTO shopOrderExportDTO = new ShopOrderExportDTO();
            shopOrderExportDTO.setOrderId(s.getOrderId());
            shopOrderExportDTO.setCreateTime(s.getCreateTime());
            shopOrderExportDTO.setCustomerName(s.getCustomerName());
            shopOrderExportDTO.setShopName(s.getShopName());
            shopOrderExportDTO.setNumber(s.getNumber());
            shopOrderExportDTO.setPrice(s.getPrice());
            shopOrderExportDTO.setPaymentTime(s.getPaymentTime());
            shopOrderExportDTO.setReceiveName(s.getReceiveName());
            shopOrderExportDTO.setReceiveAdress(s.getReceiveAdress());
            shopOrderExportDTO.setReceivePhone(s.getReceivePhone());
            shopOrderExportDTO.setRemark(s.getRemark());
            shopOrderExportDTO.setTransactionId(s.getTransactionId());
            shopOrderExportDTO.setState(OrderStateEnum.getMsgByCode(s.getState()));
            shopOrderExportDTO.setPaymentMode(PaymentModeEnum.getMsgByCode(s.getPaymentMode()));
            if (EmptyUtils.isNotEmpty(s.getProducts())) {
                StringBuilder builder = new StringBuilder();
                for (Product p : s.getProducts()) {
                    builder.append(p.getProductName())
                            .append(" ").append(p.getNumber())
                            .append(StringUtils.isNotBlank(p.getBrandName()) ? " " + p.getBrandName() : "")
                            .append(StringUtils.isNotBlank(p.getSKU()) ? " " + p.getSKU() : "")
                            .append("\n");
                }
                //String productNames=s.getProducts().stream().map(Product::getProductName).collect(Collectors.joining("\n"));
                builder.deleteCharAt(builder.length() - 1);
                shopOrderExportDTO.setProductNames(builder.toString());
            }
            shopOrderExportDTO.setHistoryOrderNum(s.getTotal());
            return shopOrderExportDTO;

        }).collect(Collectors.toList());
        //定义导出的excel名字
        String excelName = "订单列表";
        ExcelExportUtils.exportExcel(request, response, excelName, list, ShopOrderExportDTO.class);
    }

}
