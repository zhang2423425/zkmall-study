/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.product;

import com.shop.zkmall.business.page.canvas.CanvasProduct;
import com.shop.zkmall.business.page.canvas.CanvasProductParam;
import com.shop.zkmall.business.page.canvas.CanvasShop;
import com.shop.zkmall.business.page.member.ProductMember;
import com.shop.zkmall.business.page.product.ProductExportDTO;
import com.shop.zkmall.business.page.product.ShopProduct;
import com.shop.zkmall.business.page.product.SyncShopProduct;
import com.shop.zkmall.business.param.product.*;
import com.shop.zkmall.commons.domain.business.CerePlatformBusiness;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopProductService {

    void save(ProductSaveParam param, CerePlatformBusiness user);

    void update(ProductUpdateParam param, CerePlatformBusiness user);

    void delete(ProductDeleteParam param, CerePlatformBusiness user);

    ShopProduct getById(Long productId);

    void start(ProductUpDownParam param, CerePlatformBusiness user);

    Page<ShopProduct> getAll(ProductGetAllParam param);

    void importProduct(Workbook wb) throws Exception;

    CereShopProduct checkName(Long shopId, Long classifyId, String productName);

    Page<CanvasProduct> getProducts(CanvasProductParam param);

    Page<CanvasShop> getShops(CanvasProductParam param);

    List<Long> findAllZeroStockNumber();

    void updateBatchShelveState(List<Long> ids, String time);

    List<CereShopProduct> selectAll();

    List<ProductMember> getProductMembers(Long productId);

    void setProductMember(ProductMemberParam param);

    void clearProductMember(Long productId);

    void triggerCacheUpdate(Long productId);

    void updateActivitySkuStock(Long skuId, Integer activityType, Integer activityStatus,
                                Integer activityStock, Integer totalStock, long millSeconds);


    List<SyncShopProduct> syncProductList(Long shopId, Long lastProductId, Integer pageSize);

    List<ProductExportDTO> getExportList(ProductGetAllParam param);

    void cloneProduct(ProductUpdateParam param, CerePlatformBusiness user);

    void recommendProduct(RecommendProductParam param, CerePlatformBusiness user);
}
