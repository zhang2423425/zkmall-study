package com.shop.zkmall.app.param.ai;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AiDefaultQuestionResponse {
    /**
     * 关联订单id
     */
    private Long questionsId;

    /**
     * 问题内容
     */
    private String questionContent;
}
