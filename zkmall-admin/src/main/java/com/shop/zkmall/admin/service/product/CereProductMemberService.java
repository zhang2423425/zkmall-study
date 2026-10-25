/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.product;

import com.shop.zkmall.admin.page.product.MemberProduct;
import com.shop.zkmall.admin.param.product.CanvasAdminProductParam;
import com.shop.zkmall.commons.domain.common.Page;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereProductMemberService {

    Page<MemberProduct> getMemberProducts(CanvasAdminProductParam param);

}
