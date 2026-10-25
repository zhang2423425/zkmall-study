/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.business.service.checkImport.impl;

import com.shop.zkmall.business.dao.product.CereShopProductDAO;
import com.shop.zkmall.business.param.product.*;
import com.shop.zkmall.business.service.checkImport.ImportCheckService;
import com.shop.zkmall.business.service.product.CereProductClassifyService;
import com.shop.zkmall.business.service.shop.CerePlatformShopService;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.constant.StringEnum;
import com.shop.zkmall.commons.domain.product.CereProductClassify;
import com.shop.zkmall.commons.domain.product.CereShopProduct;
import com.shop.zkmall.commons.domain.shop.CerePlatformShop;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.EmptyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportCheckServiceImpl implements ImportCheckService {

    private final CereShopProductDAO cereShopProductDAO;

    private final CerePlatformShopService cerePlatformShopService;

    private final CereProductClassifyService cereProductClassifyService;

    @Override
    public List<ProductSaveParam> checkProduct(List<ProductImport> list, Long shopId) {
        StringBuilder message = new StringBuilder();
        List<ProductSaveParam> products = new ArrayList<>();
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, ProductImport> map = new HashMap<>();
            //校验是否存在重复商品名称数据
            for (ProductImport item : list) {
                if (!EmptyUtils.isEmpty(map.get(item.getProductName()))) {
                    //如果存在
                    message.append(item.getProductName()).append(";");
                } else {
                    map.put(item.getProductName(), item);
                }
            }
            if (map.size() != list.size()) {
                message.append("以上数据在表格中有重复,请修改后重试!");
            }
            //定义分类map
            Map<String, CereProductClassify> classifyMap = new HashMap<>();
            //定义商品map
            Map<String, CereShopProduct> productMap = new HashMap<>();
            //定义商家map
            //Map<String,CerePlatformShop> shopMap=new HashMap<>();
            //查询所有分类数据
            List<CereProductClassify> classifies = cereProductClassifyService.selectAll();
            if (!EmptyUtils.isEmpty(classifies)) {
                classifies.forEach((item) -> classifyMap.put(item.getClassifyHierarchy(), item));
            }
            //查询所有商品数据
            List<CereShopProduct> productList = cereShopProductDAO.selectAll();
            if (!EmptyUtils.isEmpty(productList)) {
                productList.forEach((item) -> productMap.put(item.getShopId() + "-" + item.getClassifyId() + "-" + item.getProductName(), item));
            }
            CerePlatformShop shop = cerePlatformShopService.selectByPrimaryKey(shopId);
            if (shop == null) {
                throw new CoBusinessException("商家信息异常,请重新登录");
            }
            for (int i = 0; i < list.size(); i++) {
                ProductSaveParam param = new ProductSaveParam();
                ProductImport productImport = list.get(i);
                //校验必填项
                if (EmptyUtils.isEmpty(productImport.getShopName()) || EmptyUtils.isEmpty(productImport.getOneClassifyName())
                        || EmptyUtils.isEmpty(productImport.getProductName())
                        || EmptyUtils.isEmpty(productImport.getSkuValue()) || EmptyUtils.isEmpty(productImport.getPrice())
                        || EmptyUtils.isEmpty(productImport.getOriginalPrice())) {
                    message.append("存在必填项为空的数据,请检查后重试");
                }
                //根据商家名称查询商家是否存在
                /*if(EmptyUtils.isEmpty(shopMap.get(productImport.getShopName()))){
                    message.append("第"+(i+3)+"行商家未注册;");
                }*/
                String classifyName = "";
                if (!EmptyUtils.isEmpty(productImport.getOneClassifyName())) {
                    classifyName += "-" + productImport.getOneClassifyName();
                }
                if (!EmptyUtils.isEmpty(productImport.getTwoClassifyName())) {
                    classifyName += "-" + productImport.getTwoClassifyName();
                }
                if (!EmptyUtils.isEmpty(productImport.getThreeClassifyName())) {
                    classifyName += "-" + productImport.getThreeClassifyName();
                }
                //查询分类是否存在
                if (EmptyUtils.isEmpty(classifyMap.get(classifyName))) {
                    message.append("第").append(i + 3).append("行分类不存在;");
                } else {
                    CereProductClassify classify = classifyMap.get(classifyName);
                    String key = shop.getShopId() + "-" + classify.getClassifyId() + "-" + productImport.getProductName();
                    //校验商品是否已存在
                    if (!EmptyUtils.isEmpty(productMap.get(key))) {
                        message.append("第").append(i + 3).append("行商品已存在;");
                    } else {
                        //正常商品数据封装
                        param.setShopId(shop.getShopId());
                        param.setClassifyId(classify.getClassifyId());
                        param.setProductName(productImport.getProductName());
                        param.setSupplierName(productImport.getSupplierName());
                        if (!EmptyUtils.isEmpty(productImport.getIfLogistics())) {
                            if (productImport.getIfLogistics().equals(StringEnum.YES.getCode())) {
                                param.setIfLogistics(String.valueOf(IntegerEnum.YES.getCode()));
                            } else {
                                param.setIfLogistics(String.valueOf(IntegerEnum.NO.getCode()));
                            }
                        } else {
                            param.setIfLogistics(String.valueOf(IntegerEnum.YES.getCode()));
                        }
                        if (!EmptyUtils.isEmpty(productImport.getIfOversold())) {
                            if (productImport.getIfOversold().equals(StringEnum.YES.getCode())) {
                                param.setIfOversold(String.valueOf(IntegerEnum.YES.getCode()));
                            } else {
                                param.setIfOversold(String.valueOf(IntegerEnum.NO.getCode()));
                            }
                        } else {
                            param.setIfOversold(String.valueOf(IntegerEnum.YES.getCode()));
                        }
                        if (!EmptyUtils.isEmpty(productImport.getShelveState())) {
                            if (productImport.getShelveState().equals(StringEnum.UP.getCode())) {
                                param.setShelveState(String.valueOf(IntegerEnum.YES.getCode()));
                            } else {
                                param.setShelveState(String.valueOf(IntegerEnum.NO.getCode()));
                            }
                        } else {
                            param.setShelveState(String.valueOf(IntegerEnum.YES.getCode()));
                        }
                        //封装规格数据
                        List<SkuParam> skus = new ArrayList<>();
                        SkuParam cereProductSku = new SkuParam();
                        List<SkuNameValueParam> cereSkuNames = new ArrayList<>();
                        NameValue nameValue = new NameValue();
                        nameValue.setSkuValue(productImport.getSkuValue());
                        cereProductSku.setNameValue(nameValue);
                        cereProductSku.setSkuAttrCodeDTOList(cereSkuNames);
                        cereProductSku.setPrice(productImport.getPrice());
                        cereProductSku.setOriginalPrice(productImport.getOriginalPrice());
                        cereProductSku.setStockNumber(productImport.getStockNumber());
                        cereProductSku.setWeight(productImport.getWeight());
                        skus.add(cereProductSku);
                        param.setSkus(skus);
                        products.add(param);
                    }
                }
            }
            if (!message.isEmpty()) {
                throw new CoBusinessException(message.toString());
            }
        }
        return products;
    }
}
