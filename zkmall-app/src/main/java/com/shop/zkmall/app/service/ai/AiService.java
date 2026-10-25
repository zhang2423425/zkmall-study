package com.shop.zkmall.app.service.ai;

import com.shop.zkmall.app.param.ai.AiResponse;

public interface AiService {
    AiResponse processQuery(String userInput, String type);
    
    // 新增直接提问方法，不组装商品信息
    AiResponse processDirectQuery(String userInput, String type);
}