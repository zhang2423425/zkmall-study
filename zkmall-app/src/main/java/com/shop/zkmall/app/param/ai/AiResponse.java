package com.shop.zkmall.app.param.ai;

import com.shop.zkmall.app.page.index.Product;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AiResponse {
    private String answer;
    private List<Product> products;
    private List<String> followUps;
    private boolean showRegenerate = true;
}