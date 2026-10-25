/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product.impl;

import com.shop.zkmall.app.dao.product.CereCommentWordDAO;
import com.shop.zkmall.app.page.product.CommentWord;
import com.shop.zkmall.app.service.product.CereCommentWordService;
import com.shop.zkmall.commons.domain.product.CereCommentWord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereCommentWordServiceImpl implements CereCommentWordService {

    private final CereCommentWordDAO cereCommentWordDAO;

    @Override
    public void insertBatch(List<CereCommentWord> list) {
        cereCommentWordDAO.insertBatch(list);
    }

    @Cacheable(value = "config", cacheManager = "caffeineCacheManager")
    @Override
    public List<CommentWord> findByProductId(Long productId) {
        return cereCommentWordDAO.findByProductId(productId);
    }
}
