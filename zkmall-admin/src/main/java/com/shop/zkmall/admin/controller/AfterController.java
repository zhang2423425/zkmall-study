/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.page.after.*;
import com.shop.zkmall.admin.param.after.*;
import com.shop.zkmall.admin.service.after.CereOrderAfterService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.enums.OrderStateEnum;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 售后管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("after")
@RequiredArgsConstructor
@Tag(name = "售后管理模块", description = "售后管理模块")
public class AfterController {

    private final CereOrderAfterService cereOrderAfterService;

    /**
     * 售后管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "售后管理查询")
    public Result<Page<After>> getAll(@RequestBody AfterGetAllParam param) {
        Page<After> page = cereOrderAfterService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 售后详情查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "售后详情查询")
    public Result<AfterDetail> getById(@RequestBody AfterGetByIdParam param) throws Exception {
        AfterDetail detail = cereOrderAfterService.getById(param.getAfterId());
        return new Result<>(detail);
    }

    /**
     * 物流信息查询
     */
    @PostMapping(value = "getDilevery")
    @Operation(summary = "物流信息查询")
    public Result<List<AfterDilevery>> getDilevery(@RequestBody AfterGetDileveryParam param) throws Exception {
        List<AfterDilevery> dileveries = cereOrderAfterService.getDilevery(param);
        return new Result<>(dileveries);
    }

    /**
     * 买家信息查询
     */
    @PostMapping(value = "getBuyer")
    @Operation(summary = "买家信息查询")
    public Result<AfterBuyer> getBuyer(@RequestBody AfterGetBuyerParam param) {
        AfterBuyer buyer = cereOrderAfterService.getBuyer(param.getBuyerUserId());
        return new Result<>(buyer);
    }

    /**
     * 售后处理
     */
    @PostMapping(value = "handle")
    @NoRepeatSubmit
    @Operation(summary = "售后处理")
    @WebRequestLog
    public Result<String> handle(@RequestBody AfterhandleParam param,
                                 HttpServletRequest request) throws Exception {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereOrderAfterService.handle(param, user);
        return new Result<>(user.getUsername(), "售后处理", GsonUtil.objectToGson(param));
    }

    /**
     * 售后管理导出
     */
    @PostMapping(value = "export")
    @Operation(summary = "售后管理导出")
    public void export(@RequestBody AfterGetAllParam param, HttpServletRequest request, HttpServletResponse response) {
        Page<After> page = cereOrderAfterService.getAll(param);
        List<AfterExportDTO> list = page.getList().stream().map(s -> {
            AfterExportDTO afterExportDTO = new AfterExportDTO();
            afterExportDTO.setAfterType(s.getAfterType());
            afterExportDTO.setAfterFormid(s.getAfterFormid());
            afterExportDTO.setAfterProductNames(s.getAfterProductNames());
            afterExportDTO.setExpress(s.getExpress());
            afterExportDTO.setCustomerName(s.getCustomerName());
            afterExportDTO.setOrderId(s.getOrderId());
            afterExportDTO.setNumber(s.getNumber());
            afterExportDTO.setPaymentTime(s.getPaymentTime());
            afterExportDTO.setDeliverFormid(s.getDeliverFormid());
            afterExportDTO.setPlatformAfterTime(s.getPlatformAfterTime());
            afterExportDTO.setShopName(s.getShopName());
            afterExportDTO.setShopCode(s.getShopCode());
            afterExportDTO.setOrderState(OrderStateEnum.getMsgByCode(s.getOrderState()));
            afterExportDTO.setRefundMoney(s.getRefundMoney());
            return afterExportDTO;
        }).collect(Collectors.toList());
        //定义导出的excel名字
        String excelName = "售后订单列表";
        ExcelExportUtils.exportExcel(request, response, excelName, list, AfterExportDTO.class);
    }
}
