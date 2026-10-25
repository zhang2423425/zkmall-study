/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.cart;

import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.cart.ShopCart;
import com.shop.zkmall.app.param.cart.*;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereShopCartService {

    void addCart(AddCartParam param, CereBuyerUser user);

    List<ShopCart> getCart(CereBuyerUser user);

    void delete(DeleteParam param, CereBuyerUser user);

    void deleteAll(Long buyerUserId);

    void selected(SelectedParam param, CereBuyerUser user);

    void updateNumber(CartUpdateParam param, CereBuyerUser user);

    void deleteSkus(List<CartSku> carts, Long buyerUserId);

    void addBatchCart(BatchCartParam param, CereBuyerUser user);

    void buyAgain(BuyerAgainParam param, CereBuyerUser user);

    void updateSku(UpdateSkuParam param, CereBuyerUser user);

    Integer findNumber(Long buyerUserId);

    void updateBuyerData(Long buyerUserId, Long id);

    Boolean clearInvalidSku(Long buyerUserId);
}
