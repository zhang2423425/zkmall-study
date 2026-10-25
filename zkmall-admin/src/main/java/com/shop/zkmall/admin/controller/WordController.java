/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.controller;

import com.shop.zkmall.web.annotation.NoRepeatSubmit;
import com.shop.zkmall.web.annotation.WebRequestLog;
import com.shop.zkmall.admin.param.word.WordParam;
import com.shop.zkmall.admin.service.word.CerePlatformWordService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;
import com.shop.zkmall.commons.domain.word.CerePlatformWord;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.GsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关键词
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("word")
@RequiredArgsConstructor
@Tag(name = "关键词", description = "关键词")
public class WordController {

    private final CerePlatformWordService cerePlatformWordService;

    /**
     * 添加关键词
     */
    @PostMapping(value = "save")
    @NoRepeatSubmit
    @Operation(summary = "添加关键词")
    @WebRequestLog
    public Result<String> save(@RequestBody @Validated CerePlatformWord word, HttpServletRequest request) {
        //校验关键词
        CerePlatformWord cerePlatformWord = cerePlatformWordService.checkWord(word.getKeyWord(), null);
        if (cerePlatformWord != null) {
            return new Result<>(CoReturnFormat.KEY_WORD_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.save(word, user);
        return new Result<>(user.getUsername(), "添加关键词", GsonUtil.objectToGson(word));
    }

    /**
     * 修改关键词
     */
    @PostMapping(value = "update")
    @NoRepeatSubmit
    @Operation(summary = "修改关键词")
    @WebRequestLog
    public Result<String> update(@RequestBody CerePlatformWord word, HttpServletRequest request) {
        //校验关键词
        CerePlatformWord cerePlatformWord = cerePlatformWordService.checkWord(word.getKeyWord(), word.getWordId());
        if (cerePlatformWord != null) {
            return new Result<>(CoReturnFormat.KEY_WORD_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.update(word, user);
        return new Result<>(user.getUsername(), "修改关键词", GsonUtil.objectToGson(word));
    }

    /**
     * 删除关键词
     */
    @PostMapping(value = "delete")
    @NoRepeatSubmit
    @Operation(summary = "删除关键词")
    @WebRequestLog
    public Result<String> delete(@RequestBody CerePlatformWord word, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.delete(word, user);
        return new Result<>(user.getUsername(), "删除关键词", GsonUtil.objectToGson(word));
    }

    /**
     * 关键词修改查询
     */
    @PostMapping(value = "getById")
    @Operation(summary = "关键词修改查询")
    public Result<CerePlatformWord> getById(@RequestBody CerePlatformWord word) {
        CerePlatformWord cerePlatformWord = cerePlatformWordService.getById(word.getWordId());
        return new Result<>(cerePlatformWord);
    }

    /**
     * 关键词管理查询
     */
    @PostMapping(value = "getAll")
    @Operation(summary = "关键词管理查询")
    public Result<Page<CerePlatformWord>> getAll(@RequestBody WordParam param) {
        Page<CerePlatformWord> page = cerePlatformWordService.getAll(param);
        return new Result<>(page);
    }

    /**
     * 启停用
     */
    @PostMapping(value = "start")
    @Operation(summary = "启停用")
    @WebRequestLog
    public Result<String> start(@RequestBody CerePlatformWord word, HttpServletRequest request) {
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.start(word.getState());
        return new Result<>(user.getUsername(), "启停用", GsonUtil.objectToGson(word));
    }
}
