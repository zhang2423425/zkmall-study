/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product;

import com.shop.zkmall.app.page.product.CommentWord;
import com.shop.zkmall.commons.domain.product.CereCommentWord;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereCommentWordService {

    void insertBatch(List<CereCommentWord> collect);

    List<CommentWord> findByProductId(Long productId);
}
