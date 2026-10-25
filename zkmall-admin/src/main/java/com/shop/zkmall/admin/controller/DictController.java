/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.dict.*;
import com.shop.zkmall.admin.service.dict.CerePlatformDictService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.LongEnum;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.dict.CerePlatformDict;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 数据字典
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("dict")
@Tag(name = "数据字典模块", description = "数据字典模块")
public class DictController {

    private final CerePlatformDictService cerePlatformDictService;

    /**
     * 添加数据字典
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加数据字典")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated DictSaveParam param, HttpServletRequest request) {
        //校验字典名称
        CerePlatformDict cerePlatformDict = cerePlatformDictService.checkName(param.getDictName());
        if (cerePlatformDict != null) {
            return new Result<>(CoReturnFormat.DICT_NAME_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDictService.save(param, user);
        return new Result<>(user.getUsername(), "添加数据字典", GsonUtil.objectToGson(param));
    }

    /**
     * 修改数据字典
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改数据字典")
    @WebRequestLog
    public Result<String> update(@RequestBody DictUpdateParam param, HttpServletRequest request) {
        if (param.getDictId() <= 1884L && LongEnum.ROOT.getCode().equals(param.getDictPid())) {
            //如果是初始化数据字典且是根节点,不允许编辑
            return new Result<>(CoReturnFormat.DICT_NOT_OPERATION);
        }
        //校验字典名称
        CerePlatformDict cerePlatformDict = cerePlatformDictService.checkNameAndId(param);
        if (cerePlatformDict != null) {
            return new Result<>(CoReturnFormat.DICT_NAME_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDictService.update(param, user);
        return new Result<>(user.getUsername(), "修改数据字典", GsonUtil.objectToGson(param));
    }

    /**
     * 删除数据字典
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除数据字典")
    @WebRequestLog
    public Result<String> delete(@RequestBody DictDeleteParam param, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        //查询字典数据
        CerePlatformDict dict = cerePlatformDictService.getById(param.getDictId());
        if (dict != null) {
            if (dict.getDictId() <= 1884L && LongEnum.ROOT.getCode().equals(dict.getDictPid())) {
                //如果是初始化数据字典且是根节点,不允许该操作
                return new Result<>(CoReturnFormat.DICT_NOT_OPERATION);
            }
            cerePlatformDictService.delete(param, user);
        }
        return new Result<>(user.getUsername(), "删除数据字典", GsonUtil.objectToGson(param));
    }

    /**
     * 数据字典编辑查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "数据字典编辑查询")
    public Result<CerePlatformDict> getById(@RequestBody DictGetByIdParam param) {
        CerePlatformDict cerePlatformDict = cerePlatformDictService.getById(param.getDictId());
        return new Result<>(cerePlatformDict);
    }

    /**
     * 数据字典管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "数据字典管理查询")
    public Result<Page<CerePlatformDict>> getAll(@RequestBody DictGetAllParam param) {
        Page<CerePlatformDict> page = cerePlatformDictService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 根据父节点id查询子级字典数据
     */
    @PostMapping(value = "getChilds")
    @Operation(summary = "根据父节点id查询子级字典数据")
    public Result<Page<CerePlatformDict>> getChilds(@RequestBody DictGetChildsParam param) {
        Page<CerePlatformDict> page = cerePlatformDictService.getChilds(param);
        return new Result<>(page);
    }

    /**
     * 字典下拉数据查询
     */
    @PostMapping(value = "getSelect")
    @Operation(summary = "<字典下拉数据查询")
    public Result<List<CerePlatformDict>> getSelect(@RequestBody DictGetSelectParam param) {
        List<CerePlatformDict> list = cerePlatformDictService.getSelect(param.getDictName());
        return new Result<>(list);
    }

    /**
     * 导入快递公司编码数据
     */
    @PostMapping(value = "importExpressCodes")
    @NoRepeatSubmit
    @Operation(summary = "导入快递公司编码数据", description = "file:文件")
    public Result<String> importExpressCodes(MultipartFile file) throws Exception {
        if (EmptyUtils.isEmpty(file)) {
            return new Result<>(CoReturnFormat.NOT_FILE);
        }
        Workbook wb = WorkbookFactory.create(file.getInputStream());
        cerePlatformDictService.importExpressCodes(wb);
        return new Result<>();
    }
}
