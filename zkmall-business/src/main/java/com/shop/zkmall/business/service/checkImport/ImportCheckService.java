/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.checkImport;

import com.shop.zkmall.business.param.product.ProductImport;
import com.shop.zkmall.business.param.product.ProductSaveParam;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface ImportCheckService {

    List<ProductSaveParam> checkProduct(List<ProductImport> list, Long shopId);
}
