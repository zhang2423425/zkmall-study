package com.shop.zkmall.app.controller.product;


import com.shop.zkmall.app.page.product.ProductDetail;
import com.shop.zkmall.app.param.product.ProductParam;
import com.shop.zkmall.app.service.buyer.CereBuyerUserService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final CereBuyerUserService cereBuyerUserService;

    private final CereShopProductService cereShopProductService;

    @GetMapping("getById")
    @Operation(description = "查询商品")
    public Result<ProductDetail> getById(ProductParam param, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        CereBuyerUser user = null;
        if (!EmptyUtils.isEmpty(token)){
            user=cereBuyerUserService.findByToken(token);
        }
        ProductDetail detail = cereShopProductService.getById(param, user);
        return new Result<>(detail, CoReturnFormat.SUCCESS);
    }
}
