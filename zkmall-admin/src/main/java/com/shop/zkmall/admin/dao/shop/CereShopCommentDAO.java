/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.shop.CommentDetail;
import com.shop.zkmall.admin.page.shop.ShopComment;
import com.shop.zkmall.admin.param.comment.CommentGetAllParam;
import com.shop.zkmall.commons.domain.shop.CereShopComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereShopCommentDAO extends BaseMapper<CereShopComment> {

    int insertSelective(CereShopComment record);

    List<ShopComment> getAll(CommentGetAllParam param);

    CommentDetail getById(@Param("commentId") Long commentId);

    String findSensitiveWord();

    BigDecimal selectShopStar(Long shopId);
}
