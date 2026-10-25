package com.shop.zkmall.app.service.ai.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.param.ai.AiResponse;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.service.ai.AiService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    @Value("${tencent.yuanqi.agent-id}")
    private String agentId;

    @Value("${tencent.yuanqi.token}")
    private String tencentToken;

    // 百度AI配置
    @Value("${baidu.ai.appid:}")
    private String baiduAppId;

    @Value("${baidu.ai.token:}")
    private String baiduToken;

    // 阿里AI配置
    @Value("${ali.ai.apikey:}")
    private String aliApiKey;

    private final CereShopProductService productService;

    private static final String TENCENT_API_URL = "https://open.hunyuan.tencent.com/openapi/v1/agent/chat/completions";
    private static final String BAIDU_API_URL = "https://qianfan.baidubce.com/v2/chat/completions";
    private static final String ALI_API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
    
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new Gson();

    /**
     * 商品详情前缀
     */
    @Value("${goods_url}")
    private String goodsUrl;

    @Override
    public AiResponse processQuery(String userInput, String type) {
        // 1. 搜索相关商品
        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(JSON.parseObject(userInput).get("userQuery").toString());
        List<Product> products = productService.getSearchProducts(searchParam);

        // shopId=410&productId=2874&skuId=9585
        // 拼接详情地址
        for (Product product : products) {
            product.setDetailUrl(goodsUrl + "shopId=" + product.getShopId() + "&productId=" + product.getProductId() + "&skuId=" + product.getSkuId());
        }

        // 2. 调用对应AI获取回复
        String aiResponse = "";

        // 2.1 如果用户给的关键字中查询不到相关商品即给出相应的回答
        if (!products.isEmpty()){
            aiResponse  = callAi(userInput, products, type);
        } else {
            aiResponse = "咱们商城暂时还没有此类商品，请敬请期待吧！";
        }

        // 3. 生成追问问题
        List<String> followUps = generateFollowUps(products, userInput);

        // 4. 生成最终响应
        return new AiResponse()
                .setAnswer(aiResponse)
                .setProducts(products)
                .setFollowUps(followUps)
                .setShowRegenerate(true);
    }

    // 新增直接提问处理方法，不组装商品信息
    @Override
    public AiResponse processDirectQuery(String userInput, String type) {
        // 直接调用对应AI获取回复，不传入商品信息
        String aiResponse = callAiDirect(userInput, type);

        // 生成追问问题
        List<String> followUps = generateDirectFollowUps(userInput);

        // 生成最终响应
        return new AiResponse()
                .setAnswer(aiResponse)
                .setFollowUps(followUps)
                .setShowRegenerate(true);
    }

    private String callAi(String userInput, List<Product> products, String type) {
        try {
            switch (getType(type)) {
                case "baidu":
                    return callBaiduAi(userInput, products);
                case "ali":
                    return callAliAi(userInput, products);
                case "tencent":
                default:
                    return callTencentAi(userInput, products);
            }
        } catch (Exception e) {
            log.error("AI调用失败", e);
            return generateFallbackResponse(userInput, products);
        }
    }

    // 新增直接调用AI的方法，不包含商品信息
    private String callAiDirect(String userInput, String type) {
        try {
            switch (getType(type)) {
                case "baidu":
                    return callBaiduAiDirect(userInput);
                case "ali":
                    return callAliAiDirect(userInput);
                case "tencent":
                default:
                    return callTencentAiDirect(userInput);
            }
        } catch (Exception e) {
            log.error("AI调用失败", e);
            return generateDirectFallbackResponse(userInput);
        }
    }

    private String callTencentAi(String userInput, List<Product> products) {
        try {
            String requestBody = buildTencentRequest(userInput, products);
            String response = sendTencentPostRequest(requestBody);
            return parseTencentResponse(response);
        } catch (Exception e) {
            log.error("腾讯元器API调用失败", e);
            throw new RuntimeException("腾讯元器API调用失败", e);
        }
    }

    // 新增直接调用腾讯AI的方法，不包含商品信息
    private String callTencentAiDirect(String userInput) {
        try {
            String requestBody = buildTencentDirectRequest(userInput);
            String response = sendTencentPostRequest(requestBody);
            return parseTencentResponse(response);
        } catch (Exception e) {
            log.error("腾讯元器API调用失败", e);
            throw new RuntimeException("腾讯元器API调用失败", e);
        }
    }

    private String callBaiduAi(String userInput, List<Product> products) {
        try {
            String requestBody = buildBaiduRequest(userInput, products);
            String response = sendBaiduPostRequest(requestBody);
            return parseBaiduResponse(response);
        } catch (Exception e) {
            log.error("百度AI API调用失败", e);
            throw new RuntimeException("百度AI API调用失败", e);
        }
    }

    private String callBaiduAiDirect(String userInput) {
        try {
            String requestBody = buildBaiduDirectRequest(userInput);
            String response = sendBaiduPostRequest(requestBody);
            return parseBaiduResponse(response);
        } catch (Exception e) {
            log.error("百度AI API调用失败", e);
            throw new RuntimeException("百度AI API调用失败", e);
        }
    }

    private String callAliAi(String userInput, List<Product> products) {
        try {
            String requestBody = buildAliRequest(userInput, products);
            String response = sendAliPostRequest(requestBody);
            return parseAliResponse(response);
        } catch (Exception e) {
            log.error("阿里AI API调用失败", e);
            throw new RuntimeException("阿里AI API调用失败", e);
        }
    }

    private String callAliAiDirect(String userInput) {
        try {
            String requestBody = buildAliDirectRequest(userInput);
            String response = sendAliPostRequest(requestBody);
            return parseAliResponse(response);
        } catch (Exception e) {
            log.error("阿里AI API调用失败", e);
            throw new RuntimeException("阿里AI API调用失败", e);
        }
    }

    private String buildTencentRequest(String userInput, List<Product> products) {
        JsonObject request = new JsonObject();
        request.addProperty("assistant_id", agentId);
        request.addProperty("user_id", UUID.randomUUID().toString());
        request.addProperty("stream", false);

        // 构建消息内容
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("用户询问：").append(userInput).append("\n\n");

        if (!products.isEmpty()) {
            contentBuilder.append("找到以下相关商品信息：\n");
            for (int i = 0; i < Math.min(products.size(), 5); i++) {
                Product product = products.get(i);
                contentBuilder.append("商品名称：").append(product.getProductName())
                        .append("，价格：¥").append(product.getPrice());
                if (product.getProductBrief() != null) {
                    contentBuilder.append("，简介：").append(product.getProductBrief());
                }
                contentBuilder.append("\n");
            }
            contentBuilder.append("\n请根据这些商品信息，为用户提供专业的采购建议和推荐。");
        } else {
            contentBuilder.append("未找到直接相关的商品，请根据用户需求提供采购建议和推荐。");
        }

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");

        JsonArray contentArray = new JsonArray();
        JsonObject textContent = new JsonObject();
        textContent.addProperty("type", "text");
        textContent.addProperty("text", contentBuilder.toString());
        contentArray.add(textContent);

        message.add("content", contentArray);
        messages.add(message);

        request.add("messages", messages);

        return new Gson().toJson(request);
    }

    // 新增构建腾讯直接请求的方法，不包含商品信息
    private String buildTencentDirectRequest(String userInput) {
        JsonObject request = new JsonObject();
        request.addProperty("assistant_id", agentId);
        request.addProperty("user_id", UUID.randomUUID().toString());
        request.addProperty("stream", false);

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");

        JsonArray contentArray = new JsonArray();
        JsonObject textContent = new JsonObject();
        textContent.addProperty("type", "text");
        textContent.addProperty("text", userInput);
        contentArray.add(textContent);

        message.add("content", contentArray);
        messages.add(message);

        request.add("messages", messages);

        return new Gson().toJson(request);
    }

    private String buildBaiduRequest(String userInput, List<Product> products) {
        JsonObject request = new JsonObject();
        request.addProperty("model", "ernie-3.5-128k");

        // 构建消息内容
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("用户询问：").append(userInput).append("\n\n");

        if (!products.isEmpty()) {
            contentBuilder.append("找到以下相关商品信息：\n");
            for (int i = 0; i < Math.min(products.size(), 5); i++) {
                Product product = products.get(i);
                contentBuilder.append("商品名称：").append(product.getProductName())
                        .append("，价格：¥").append(product.getPrice());
                if (product.getProductBrief() != null) {
                    contentBuilder.append("，简介：").append(product.getProductBrief());
                }
                contentBuilder.append("\n");
            }
            contentBuilder.append("\n请根据这些商品信息，为用户提供专业的采购建议和推荐。");
        } else {
            contentBuilder.append("未找到直接相关的商品，请根据用户需求提供采购建议和推荐。");
        }

        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");

        JsonArray contentArray = new JsonArray();
        JsonObject textContent = new JsonObject();
        textContent.addProperty("type", "text");
        textContent.addProperty("text", contentBuilder.toString());
        contentArray.add(textContent);
        userMessage.add("content", contentArray);

        messages.add(userMessage);
        request.add("messages", messages);

        JsonObject webSearch = new JsonObject();
        webSearch.addProperty("enable", false);
        request.add("web_search", webSearch);

        return new Gson().toJson(request);
    }

    private String buildBaiduDirectRequest(String userInput) {
        JsonObject request = new JsonObject();
        request.addProperty("model", "ernie-3.5-128k");

        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");

        JsonArray contentArray = new JsonArray();
        JsonObject textContent = new JsonObject();
        textContent.addProperty("type", "text");
        textContent.addProperty("text", userInput);
        contentArray.add(textContent);
        userMessage.add("content", contentArray);

        messages.add(userMessage);
        request.add("messages", messages);

        JsonObject webSearch = new JsonObject();
        webSearch.addProperty("enable", false);
        request.add("web_search", webSearch);

        return new Gson().toJson(request);
    }

    private String buildAliRequest(String userInput, List<Product> products) {
        JsonObject request = new JsonObject();
        request.addProperty("model", "qwen-turbo");
        request.addProperty("stream", false);

        JsonObject parameters = new JsonObject();
        parameters.addProperty("max_tokens", 1024);
        parameters.addProperty("temperature", 0.7);
        request.add("parameters", parameters);

        // 构建消息内容
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("用户询问：").append(userInput).append("\n\n");

        if (!products.isEmpty()) {
            contentBuilder.append("找到以下相关商品信息：\n");
            for (int i = 0; i < Math.min(products.size(), 5); i++) {
                Product product = products.get(i);
                contentBuilder.append("商品名称：").append(product.getProductName())
                        .append("，价格：¥").append(product.getPrice());
                if (product.getProductBrief() != null) {
                    contentBuilder.append("，简介：").append(product.getProductBrief());
                }
                contentBuilder.append("\n");
            }
            contentBuilder.append("\n请根据这些商品信息，为用户提供专业的采购建议和推荐。");
        } else {
            contentBuilder.append("未找到直接相关的商品，请根据用户需求提供采购建议和推荐。");
        }

        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", contentBuilder.toString());
        messages.add(userMessage);

        request.add("messages", messages);

        return new Gson().toJson(request);
    }

    private String buildAliDirectRequest(String userInput) {
        JsonObject request = new JsonObject();
        request.addProperty("model", "qwen-turbo");
        request.addProperty("stream", false);

        JsonObject parameters = new JsonObject();
        parameters.addProperty("max_tokens", 1024);
        parameters.addProperty("temperature", 0.7);
        request.add("parameters", parameters);

        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", userInput);
        messages.add(userMessage);

        request.add("messages", messages);

        return new Gson().toJson(request);
    }

    private String sendTencentPostRequest(String requestBody) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(TENCENT_API_URL)
                    .addHeader("Authorization", "Bearer " + tencentToken)
                    .addHeader("X-Source", "openapi")
                    .post(RequestBody.create(requestBody, JSON_MEDIA_TYPE))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("请求失败，状态码: " + response.code());
                }

                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new RuntimeException("响应体为空");
                }

                return responseBody.string();
            }
        } catch (Exception e) {
            throw new RuntimeException("发送腾讯API请求失败", e);
        }
    }

    private String sendBaiduPostRequest(String requestBody) {
        try {
            MediaType mediaType = MediaType.get("application/json");
            RequestBody body = RequestBody.create(requestBody, mediaType);
            Request request = new Request.Builder()
                    .url(BAIDU_API_URL)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("appid", baiduAppId)
                    .addHeader("Authorization", "Bearer " + baiduToken)
                    .build();
            
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("发送百度API请求失败", e);
        }
    }

    private String sendAliPostRequest(String requestBody) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(ALI_API_URL)
                    .addHeader("Authorization", "Bearer " + aliApiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(requestBody, JSON_MEDIA_TYPE))
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("发送阿里API请求失败", e);
        }
    }

    private String parseTencentResponse(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            if (!element.isJsonObject()) {
                throw new RuntimeException("无效的API响应格式");
            }

            JsonObject obj = element.getAsJsonObject();
            JsonArray choices = obj.getAsJsonArray("choices");

            if (choices == null || choices.size() == 0) {
                throw new RuntimeException("未获取到有效回复内容");
            }

            return choices.get(0).getAsJsonObject()
                    .get("message").getAsJsonObject()
                    .get("content").getAsString();
        } catch (Exception e) {
            log.error("解析腾讯API响应失败", e);
            throw new RuntimeException("解析AI回复失败");
        }
    }

    private String parseBaiduResponse(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            if (!element.isJsonObject()) {
                throw new RuntimeException("无效的API响应格式");
            }

            JsonObject obj = element.getAsJsonObject();
            JsonArray choices = obj.getAsJsonArray("choices");

            if (choices == null || choices.size() == 0) {
                throw new RuntimeException("未获取到有效回复内容");
            }

            return choices.get(0).getAsJsonObject()
                    .get("message").getAsJsonObject()
                    .get("content").getAsString();
        } catch (Exception e) {
            log.error("解析百度API响应失败", e);
            throw new RuntimeException("解析AI回复失败");
        }
    }

    private String parseAliResponse(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            if (!element.isJsonObject()) {
                throw new RuntimeException("无效的API响应格式");
            }

            JsonObject obj = element.getAsJsonObject();
            JsonArray choices = obj.getAsJsonArray("choices");

            if (choices == null || choices.size() == 0) {
                throw new RuntimeException("未获取到有效回复内容");
            }

            return choices.get(0).getAsJsonObject()
                    .get("message").getAsJsonObject()
                    .get("content").getAsString();
        } catch (Exception e) {
            log.error("解析阿里API响应失败", e);
            throw new RuntimeException("解析AI回复失败");
        }
    }

    private String generateFallbackResponse(String userInput, List<Product> products) {
        StringBuilder response = new StringBuilder();
        response.append("``intro\n了解您的需求，您正在寻找相关商品信息。注意因素：质量、价格、品牌、服务。\n```\n\n");

        if (!products.isEmpty()) {
            response.append("根据您的需求，我为您找到了以下相关商品：\n\n");
            for (int i = 0; i < Math.min(products.size(), 3); i++) {
                Product product = products.get(i);
                response.append("**").append(product.getProductName()).append("**\n");
                response.append("- 价格：¥").append(product.getPrice()).append("\n");
                if (product.getProductBrief() != null) {
                    response.append("- 简介：").append(product.getProductBrief()).append("\n");
                }
                response.append("\n");
            }
            response.append("您对这些商品有什么具体要求吗？比如规格、材质、品牌偏好等？");
        } else {
            response.append("根据您的需求，我为您提供以下建议：\n\n");
            response.append("**采购建议：**\n");
            response.append("• 明确您的具体需求和预算范围\n");
            response.append("• 考虑商品的质量标准和认证要求\n");
            response.append("• 对比不同商家的价格和服务\n");
            response.append("• 关注商家的信誉和售后服务\n\n");
            response.append("您对这类商品有什么具体要求吗？比如规格、材质、品牌偏好等？");
        }

        return response.toString();
    }

    // 新增生成直接提问的备用响应方法
    private String generateDirectFallbackResponse(String userInput) {
        StringBuilder response = new StringBuilder();
        response.append("感谢您的提问！\n\n");
        response.append("我已经收到您的问题：").append(userInput).append("\n\n");
        response.append("我会尽力为您提供相关信息和建议。如果您有更具体的需求或想了解更多细节，请随时告诉我！");

        return response.toString();
    }

    private List<String> generateFollowUps(List<Product> products, String userInput) {
        List<String> followUps = new ArrayList<>();

        if (!products.isEmpty()) {
            followUps.add("您对这些商品有什么具体要求吗？");
            followUps.add("您的预算范围是多少？");
            followUps.add("需要什么规格的产品？");
        } else {
            // 根据用户输入关键词生成相关追问
            if (userInput.contains("锁") || userInput.contains("门锁")) {
                followUps.add("您对材质有特殊要求吗？比如不锈钢、锌合金等");
                followUps.add("需要什么尺寸规格的产品？");
                followUps.add("您的预算范围是多少？");
            } else if (userInput.contains("电脑") || userInput.contains("计算机")) {
                followUps.add("您更偏好哪种类型的电脑?比如工控机、一体机或者平板电脑?");
                followUps.add("对配置有什么特殊要求吗？");
                followUps.add("预算范围是多少？");
            } else {
                followUps.add("您还需要了解什么其他信息？");
                followUps.add("有其他相关需求吗？");
            }
        }

        return followUps;
    }

    // 新增生成直接提问追问的方法
    private List<String> generateDirectFollowUps(String userInput) {
        List<String> followUps = new ArrayList<>();
        followUps.add("您还有其他相关问题吗？");
        followUps.add("您希望了解更详细的信息吗？");
        followUps.add("有什么我可以进一步帮助您的吗？");
        return followUps;
    }

    private String getType(String type) {
        if (type == null || type.isEmpty()) {
            return "tencent"; // 默认使用腾讯
        }
        return type.toLowerCase();
    }
}