/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product;

import com.shop.zkmall.app.page.canvas.CanvasProduct;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.product.BroadCastDTO;
import com.shop.zkmall.app.page.product.ProductDetail;
import com.shop.zkmall.app.page.product.ProductSkus;
import com.shop.zkmall.app.page.product.ShareProduct;
import com.shop.zkmall.app.param.canvas.CanvasAppProductParam;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.param.product.PageProductParam;
import com.shop.zkmall.app.param.product.ProductParam;
import com.shop.zkmall.app.param.product.SkuParam;
import com.shop.zkmall.commons.cache.product.ProductBo;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import com.shop.zkmall.commons.domain.product.Classify;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopProductService {

    CereShopProduct selectByPrimaryKey(Long id);

    ProductDetail getById(ProductParam param, CereBuyerUser user) throws Exception;

    ProductSkus getProducts(SkuParam param);

    List<Product> getSearchProducts(SearchParam param);

    Page<CanvasProduct> getCanvasProducts(CanvasAppProductParam param);

    Page<CanvasProduct> getProducts2(CanvasAppProductParam param);

    List<Classify> getClassify();

    List<Classify> getClassifyByIds(List<Long> ids);

    void deleteAllSearch(CereBuyerUser user);

    List<Long> findClassifyNumber(Long shopId);

    InputStream getSharePic(ProductParam param, CereBuyerUser user) throws Exception;

    Page<Product> getRandomSortProduct(PageProductParam param, Long buyerUserId);

    ProductBo fetchProductCache(Long shopId, Long productId, Long skuId, CereBuyerUser user, boolean needRealtimeStock);

    Map<Long, Product> getSimpleActivityInfo(Long shopId, List<Long> skuIdList, CereBuyerUser user);

    int selectFictitiousNumber(Long productId);

    ProductSkus getSpecificSku(SkuParam param);

    List<BroadCastDTO> getBroadCastList(Long productId, Long shopGroupWorkId, Long buyerUserId);

    ShareProduct getShareProductParam(ProductParam param, CereBuyerUser user) throws Exception;

    Page<CanvasProduct> selectRecommendProductList(Long buyerUserId, CanvasAppProductParam param);

}
