/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.order;

import com.shop.zkmall.commons.domain.order.CereOrderProduct;
import com.shop.zkmall.commons.domain.order.CereOrderProductAttribute;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereOrderProductService {

    void insert(CereOrderProduct orderProduct);

    void insertBatchAttibutes(List<CereOrderProductAttribute> attributes);

}
