package com.shop.zkmall.admin.param.ai;

import com.shop.zkmall.commons.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "AI默认问题查询参数")
public class CereAiDefaultQuestionParam extends PageParam {

    @Schema(description = "问题内容")
    private String questionContent;

    @Schema(description = "创建时间开始")
    private String createTimeStart;

    @Schema(description = "创建时间结束")
    private String createTimeEnd;

}