/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.product;

import com.shop.zkmall.admin.page.product.*;
import com.shop.zkmall.admin.param.canvas.CanvasActivity;
import com.shop.zkmall.admin.param.product.CanvasAdminProductParam;
import com.shop.zkmall.admin.param.product.ProductGetAllParam;
import com.shop.zkmall.admin.param.product.RecommendProductParam;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopProductService {

    Page<CanvasProduct> getProducts(CanvasAdminProductParam param) throws Exception;

    Page<CanvasShop> getShops(CanvasAdminProductParam param);

    Page<ShopProduct> getAll(ProductGetAllParam param);

    ProductDetail getById(Long productId);

    boolean examine(ProductExamineParam param, CerePlatformUser user);

    void setFictitious(ProductUpdateParam param, CerePlatformUser user);

    void forced(ProductUpdateParam param, CerePlatformUser user);

    void unShelveByShopId(Long shopId, CerePlatformUser user);

    void triggerCacheUpdate(Long productId);

    void updateActivityProductStock(Long productId, Integer activityType, Integer activityStatus,
                                    Integer activityStock, Integer totalStock, long millSeconds);

    List<ProductExportDTO> getExportList(ProductGetAllParam param);

    Page<CanvasProduct> selectRecommendProductList(CanvasAdminProductParam param);

    List<CanvasActivity> selectMostNewActivity();

    void recommendProduct(RecommendProductParam param, CerePlatformUser user);
}
