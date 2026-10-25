/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.dao.label;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.business.page.shop.LabelSource;
import com.shop.zkmall.business.param.label.LabelGetSourceParam;
import com.shop.zkmall.commons.domain.label.CereLabelSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereLabelSourceDAO extends BaseMapper<CereLabelSource> {

    int insertSelective(CereLabelSource record);

    List<CereLabelSource> findByLabelId(@Param("labelId") Long labelId);

    void deleteByLabelId(@Param("labelId") Long labelId);

    void insertBatch(@Param("list") List<CereLabelSource> list);

    List<LabelSource> getAllByLabel(LabelGetSourceParam param);

    void deleteByImageAndLabelId(@Param("labelId") Long labelId,
                                 @Param("image") String image);

    List<CereLabelSource> findByLabelIdAndType(@Param("labelId") Long labelId, @Param("labelType") Integer labelType);

    void updateData(CereLabelSource labelSource);
}
