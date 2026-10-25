/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.app.page.product.CommentWord;
import com.shop.zkmall.commons.domain.product.CereCommentWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereCommentWordDAO extends BaseMapper<CereCommentWord> {

    int insertSelective(CereCommentWord record);

    void insertBatch(@Param("list") List<CereCommentWord> list);

    List<CommentWord> findByProductId(@Param("productId") Long productId);
}
