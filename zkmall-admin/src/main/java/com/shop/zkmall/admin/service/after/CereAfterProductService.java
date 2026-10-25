/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.after;

import com.shop.zkmall.commons.domain.after.CereAfterProduct;
import com.shop.zkmall.commons.domain.after.CereAfterProductAttribute;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereAfterProductService {

    void insert(CereAfterProduct afterProduct);

    void insertBatchAttibutes(List<CereAfterProductAttribute> attributes);
}
