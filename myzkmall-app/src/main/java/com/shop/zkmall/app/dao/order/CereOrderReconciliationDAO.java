/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.order.CereOrderReconciliation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CereOrderReconciliationDAO extends BaseMapper<CereOrderReconciliation> {

    int deleteByPrimaryKey(Long reconciliationId);

    int insertSelective(CereOrderReconciliation record);

    CereOrderReconciliation selectByPrimaryKey(Long reconciliationId);

    int updateByPrimaryKeySelective(CereOrderReconciliation record);

    int updateByPrimaryKey(CereOrderReconciliation record);

    void updateByOrderId(CereOrderReconciliation reconciliation);
}
