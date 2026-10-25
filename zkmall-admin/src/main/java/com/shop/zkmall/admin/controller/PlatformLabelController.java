/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.label.*;
import com.shop.zkmall.admin.service.label.CerePlatformLabelService;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.label.PlatformLabel;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.poi.ExcelUtils;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标签管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("label")
@RequiredArgsConstructor
@Tag(name = "标签管理", description = "标签管理")
public class PlatformLabelController {

    private final CerePlatformLabelService cerePlatformLabelService;

    /**
     * 标签管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "标签管理查询")
    public Result<Page<PlatformLabel>> getAll(@RequestBody LabelGetAllParam param) {
        Page<PlatformLabel> page = cerePlatformLabelService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 标签编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "标签编辑查询")
    public Result<PlatformLabel> getById(@RequestBody LabelGetByIdParam param) {
        PlatformLabel label = cerePlatformLabelService.getById(param.getBuyerLabelId());
        return new Result<>(label);
    }

    /**
     * 添加标签
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加标签")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated LabelSaveParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformLabelService.save(param, user);
        return new Result<>(user.getUsername(), "添加标签", GsonUtil.objectToGson(param));
    }

    /**
     * 修改标签
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改标签")
    @WebRequestLog
    public Result<String> update(@RequestBody LabelUpdateParam label, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformLabelService.update(label, user);
        return new Result<>(user.getUsername(), "修改标签", GsonUtil.objectToGson(label));
    }

    /**
     * 删除标签
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除标签")
    @WebRequestLog
    public Result<String> delete(@RequestBody LabelDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformLabelService.delete(param, user);
        return new Result<>(user.getUsername(), "删除标签", GsonUtil.objectToGson(param));
    }

    /**
     * 标签导出
     */
    @PostMapping(value = "excel_platform_label")
    @Operation(summary = "标签导出")
    public void excelLabel(@RequestBody LabelExcelParam param, HttpServletResponse response) throws Exception {
        List<PlatformLabel> list = cerePlatformLabelService.findExcel(param);
        List<String> titles = new ArrayList<>();
        titles.add("标签名");
        titles.add("客户");
        titles.add("标签类型");
        titles.add("达标条件");
        XSSFWorkbook excel = ExcelUtils.createPlatformLabelExcel(titles, list);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "订单管理表" + sdf.format(date);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + str + ".xls");
        response.flushBuffer();
        excel.write(response.getOutputStream());
        excel.close();
    }
}
