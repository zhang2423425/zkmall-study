/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.page.product;

import com.shop.zkmall.commons.poi.export.ExcelResources;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
public class ProductExportDTO {

    @ExcelResources(order = 0, title = "商品id", cloumn = 15)
    private Long productId;

    @ExcelResources(order = 1, title = "商品名称", cloumn = 25)
    private String productName;

    @ExcelResources(order = 2, title = "sku规格", cloumn = 15)
    private String skuName;

    @ExcelResources(order = 3, title = "原价", cloumn = 15)
    private BigDecimal originalPrice;

    @ExcelResources(order = 4, title = "售价", cloumn = 15)
    private BigDecimal price;

    @ExcelResources(order = 5, title = "库存", cloumn = 15)
    private Integer stockNumber;

    @ExcelResources(order = 6, title = "销量", cloumn = 15)
    private Integer salesVolume;

    @ExcelResources(order = 7, title = "上架状态", cloumn = 15)
    private String shelveState;

    @ExcelResources(order = 8, title = "品牌名称", cloumn = 15)
    private String brandName;

    @ExcelResources(order = 9, title = "国际条码", cloumn = 15)
    private String SKU;
}
