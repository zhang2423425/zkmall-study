package com.shop.zkmall.app.controller.ai;


import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.param.ai.AiDefaultQuestionResponse;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.service.ai.AiService;
import com.shop.zkmall.app.service.ai.CereAiDefaultQuestionService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.param.ai.AiResponse;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.ai.CereAiDefaultQuestion;
import com.shop.zkmall.commons.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : Super_XIE
 * @Date: 2025/7/25 14:33
 * @ClassName: AIProductController
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("ai-prod")
@RequiredArgsConstructor
@Tag(name = "ai商品", description = "ai模块")
public class AIProductController {

    private final CereShopProductService cereShopProductService;

    private final AiService aiService;

    private final CereAiDefaultQuestionService cereAiDefaultQuestionService;

    @GetMapping("search")
    @Operation(summary = "获取商品")
    public Result<?> list(SearchParam param) {
        List<Product> searchProducts = cereShopProductService.getSearchProducts(param);
        return new Result<>(searchProducts, CoReturnFormat.SUCCESS);
    }

    @PostMapping("send")
    @Operation(summary = "发送消息")
    public Result<AiResponse> sendMessage(@RequestBody String message,
                                          @RequestParam(required = false, defaultValue = "tencent") String type) {
        try {
            AiResponse response = aiService.processQuery(message, type);
            return new Result<>(response, CoReturnFormat.SUCCESS);
        } catch (Exception e) {
            log.error("处理AI请求失败", e);
            return new Result<>(CoReturnFormat.SYS_ERROR);
        }
    }

    @PostMapping("regenerate")
    @Operation(summary = "重新生成回复")
    public Result<AiResponse> regenerateResponse(@RequestBody String message,
                                                 @RequestParam(required = false, defaultValue = "tencent") String type) {
        try {
            AiResponse response = aiService.processQuery(message, type);
            return new Result<>(response, CoReturnFormat.SUCCESS);
        } catch (Exception e) {
            log.error("重新生成回复失败", e);
            return new Result<>(CoReturnFormat.SYS_ERROR);
        }
    }

    @PostMapping("direct")
    @Operation(summary = "直接提问")
    public Result<AiResponse> sendDirectMessage(@RequestBody String message,
                                                @RequestParam(required = false, defaultValue = "tencent") String type) {
        try {
            AiResponse response = aiService.processQuery(message, type);
            return new Result<>(response, CoReturnFormat.SUCCESS);
        } catch (Exception e) {
            log.error("处理AI直接提问失败", e);
            return new Result<>(CoReturnFormat.SYS_ERROR);
        }
    }

    // 获取默认问题
    @GetMapping("default-question")
    @Operation(summary = "获取默认问题")
    public Result<List<AiDefaultQuestionResponse>> getDefaultQuestion() {
        List<CereAiDefaultQuestion> questions = cereAiDefaultQuestionService.listAll();
        List<AiDefaultQuestionResponse> responses = questions.stream()
                .map(question -> {
                    AiDefaultQuestionResponse response = new AiDefaultQuestionResponse();
                    response.setQuestionsId(question.getQuestionsId());
                    response.setQuestionContent(question.getQuestionContent());
                    return response;
                })
                .collect(java.util.stream.Collectors.toList());
        return new Result<>(responses, CoReturnFormat.SUCCESS);
    }
    
    // 获取默认问题答案
    @PostMapping("default-question-answer")
    @Operation(summary = "获取默认问题答案")
    public Result<AiResponse> getDefaultQuestionAnswer(@RequestBody Long id) {
        try {
            CereAiDefaultQuestion cereAiDefaultQuestion = cereAiDefaultQuestionService.getById(id);
            AiResponse response = new AiResponse();
            
            // 解析答案列表并随机选择一个
            String answerListJson = cereAiDefaultQuestion.getAnswerList();
            // 解析JSON数组并随机选择一个答案
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.List<String> answerList = objectMapper.readValue(answerListJson, java.util.List.class);
            // 随机选择一个答案
            String randomAnswer = answerList.get(new java.util.Random().nextInt(answerList.size()));
            response.setAnswer(randomAnswer);
            return new Result<>(response, CoReturnFormat.SUCCESS);
        } catch (Exception e) {
            log.error("处理AI默认问题失败", e);
            return new Result<>(CoReturnFormat.SYS_ERROR);
        }
        
    }

}