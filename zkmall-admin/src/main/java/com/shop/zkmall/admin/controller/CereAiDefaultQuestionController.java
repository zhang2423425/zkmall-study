package com.shop.zkmall.admin.controller;

import com.shop.zkmall.admin.service.ai.CereAiDefaultQuestionService;
import com.shop.zkmall.commons.domain.ai.CereAiDefaultQuestion;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.admin.param.ai.CereAiDefaultQuestionParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI默认问题管理
 *
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@RestController
@RequestMapping("ai-default-question")
@RequiredArgsConstructor
@Tag(name = "AI默认问题管理", description = "AI默认问题管理")
public class CereAiDefaultQuestionController {

    private final CereAiDefaultQuestionService cereAiDefaultQuestionService;

    /**
     * 查询所有默认问题
     */
    @GetMapping("listAll")
    @Operation(summary = "查询所有默认问题")
    public Result<List<CereAiDefaultQuestion>> listAll() {
        List<CereAiDefaultQuestion> list = cereAiDefaultQuestionService.listAll();
        return new Result<>(list);
    }

    /**
     * 分页查询默认问题
     */
    @PostMapping("page")
    @Operation(summary = "分页查询默认问题")
    public Result<Page<CereAiDefaultQuestion>> page(@RequestBody CereAiDefaultQuestionParam param) {
        // 这里需要实现分页逻辑，由于没有看到具体的分页实现方式，暂时返回全部数据
        List<CereAiDefaultQuestion> list = cereAiDefaultQuestionService.listAll();
        Page<CereAiDefaultQuestion> page = new Page<>(list, list.size());
        return new Result<>(page);
    }

    /**
     * 根据ID获取默认问题详情
     */
    @GetMapping("getById")
    @Operation(summary = "根据ID获取默认问题详情")
    public Result<CereAiDefaultQuestion> getById(@RequestParam Long id) {
        CereAiDefaultQuestion question = cereAiDefaultQuestionService.getById(id);
        return new Result<>(question);
    }

    /**
     * 添加默认问题
     */
    @PostMapping("add")
    @Operation(summary = "添加默认问题")
    public Result<Boolean> add(@RequestBody CereAiDefaultQuestion question) {
        boolean result = cereAiDefaultQuestionService.addQuestion(question);
        return new Result<>(result);
    }

    /**
     * 更新默认问题
     */
    @PostMapping("update")
    @Operation(summary = "更新默认问题")
    public Result<Boolean> update(@RequestBody CereAiDefaultQuestion question) {
        boolean result = cereAiDefaultQuestionService.updateQuestion(question);
        return new Result<>(result);
    }

    /**
     * 删除默认问题
     */
    @PostMapping("delete")
    @Operation(summary = "删除默认问题")
    public Result<Boolean> delete(@RequestBody Long id) {
        boolean result = cereAiDefaultQuestionService.deleteById(id);
        return new Result<>(result);
    }
}