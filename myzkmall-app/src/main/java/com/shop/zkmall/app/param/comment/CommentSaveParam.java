/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.param.comment;

import com.shop.zkmall.app.param.order.OrderCommentParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 评论请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CommentSaveParam", description = "评论请求")
public class CommentSaveParam {

    /**
     * 商品评论数据
     */
    private List<OrderCommentParam> params;
}
