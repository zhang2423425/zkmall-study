/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.shop;

import com.shop.zkmall.business.page.group.Group;
import com.shop.zkmall.business.page.group.GroupDetail;
import com.shop.zkmall.business.page.group.GroupProduct;
import com.shop.zkmall.business.page.order.Product;
import com.shop.zkmall.business.param.group.*;
import com.shop.zkmall.business.param.product.ProductGetGroupParam;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.shop.CereShopGroup;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopGroupService {

    List<CereShopGroup> getGroupSelect(ProductGetGroupParam param);

    void save(ShopGroupSaveParam group, CerePlatformBusiness user);

    void update(ShopGroupUpdateParam group, CerePlatformBusiness user);

    void delete(GroupDeleteParam param, CerePlatformBusiness user);

    GroupDetail getById(Long shopGroupId);

    Page<Group> getAll(GroupGetAllParam param);

    Page<GroupProduct> getProducts(GroupProductParam param);

    List<Product> getProductIds(ShopGroupSaveParam group);
}
