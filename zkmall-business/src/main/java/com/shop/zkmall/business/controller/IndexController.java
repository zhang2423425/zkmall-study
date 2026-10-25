/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.controller;

import com.shop.zkmall.business.page.index.HotProductDTO;
import com.shop.zkmall.business.page.index.Index;
import com.shop.zkmall.business.page.index.OrderConvertDTO;
import com.shop.zkmall.business.page.index.UserVisitDTO;
import com.shop.zkmall.business.param.index.IndexParam;
import com.shop.zkmall.business.service.shop.CerePlatformShopService;
import com.shop.zkmall.business.utils.ContextUtil;
import com.shop.zkmall.commons.poi.export.ExcelExportUtils;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页概况
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("index")
@RequiredArgsConstructor
@Tag(name = "首页概况", description = "首页概况")
public class IndexController {

    private final CerePlatformShopService cerePlatformShopService;

    /**
     * 商户端首页概况数据查询
     */
    @PostMapping(value = "index")
    @Operation(summary = "商户端首页概况数据查询")
    public Result<Index> index(@RequestBody IndexParam param) {
        param.setShopId(ContextUtil.getShopId());
        Index index = cerePlatformShopService.index(param);
        return new Result<>(index);
    }

    /**
     * 导出用户访问趋势
     */
    @RequestMapping(value = "exportUserVisit", method = RequestMethod.POST)
    @Operation(summary = "商户端首页概况数据查询")
    public void exportUserVisit(@RequestBody IndexParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setShopId(ContextUtil.getShopId());
        List<UserVisitDTO> visitDTOList = cerePlatformShopService.selectUserVisitForExport(param);
        log.info("visitDTOList {}", visitDTOList);
        String excelName = "用户访问统计表";
        ExcelExportUtils.exportExcel(request, response, excelName, visitDTOList, UserVisitDTO.class);
    }

    /**
     * 导出订单转换漏斗
     */
    @RequestMapping(value = "exportOrderConvert", method = RequestMethod.POST)
    @Operation(summary = "导出订单转换漏斗")
    public void exportOrderConvert(@RequestBody IndexParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setShopId(ContextUtil.getShopId());
        List<OrderConvertDTO> visitDTOList = cerePlatformShopService.selectOrderConvertForExport(param);
        String excelName = "订单转化统计表";
        ExcelExportUtils.exportExcel(request, response, excelName, visitDTOList, OrderConvertDTO.class);
    }

    /**
     * 导出热卖商品
     */
    @RequestMapping(value = "exportHotProducts", method = RequestMethod.POST)
    @Operation(summary = "导出热卖商品")
    public void exportHotProducts(@RequestBody IndexParam param, HttpServletRequest request, HttpServletResponse response) {
        param.setShopId(ContextUtil.getShopId());
        List<HotProductDTO> visitDTOList = cerePlatformShopService.selectHotProductForExport(param);
        String excelName = "热卖商品统计表";
        ExcelExportUtils.exportExcel(request, response, excelName, visitDTOList, HotProductDTO.class);
    }
}
