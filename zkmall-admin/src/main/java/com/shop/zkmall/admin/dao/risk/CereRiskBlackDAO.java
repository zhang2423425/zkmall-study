/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.risk;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.admin.page.risk.CereRiskUserBlack;
import com.shop.zkmall.admin.param.risk.CereRiskBlackPageParam;
import com.shop.zkmall.commons.domain.risk.CereRiskBlack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 黑名单表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereRiskBlackDAO extends BaseMapper<CereRiskBlack> {

    List<CereRiskUserBlack> getUserBlackList(CereRiskBlackPageParam param);
}
