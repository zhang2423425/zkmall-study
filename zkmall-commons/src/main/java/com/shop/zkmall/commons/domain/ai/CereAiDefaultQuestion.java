/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;

/**
 * cere_after_dilever Ai默认问题表实体
 */
@Data
public class CereAiDefaultQuestion implements Serializable {
    /**
     * id
     */
    @Schema(description = "id")
    @TableId(value = "questions_id", type = IdType.AUTO)
    private Long questionsId;

    /**
     * 问题内容
     */
    @Schema(description = "问题内容")
    private String questionContent;

    /**
     * 问题答案 （多个答案 json格式）
     */
    @Schema(description = "问题答案 （多个答案 json格式）如 [\"答案1\", \"答案2\", \"答案3\"]")
    private String answerList;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}