/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.service.product.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luciad.imageio.webp.WebPReadParam;
import com.shop.zkmall.app.dao.product.CereProductParameterDAO;
import com.shop.zkmall.app.dao.product.CereProductStatsByDayDAO;
import com.shop.zkmall.app.dao.product.CereShopProductDAO;
import com.shop.zkmall.app.dao.product.CereSkuMemberRealInfoDAO;
import com.shop.zkmall.app.jobs.MyXxlJobHandler;
import com.shop.zkmall.app.page.canvas.CanvasProduct;
import com.shop.zkmall.app.page.canvas.CanvasProductNumber;
import com.shop.zkmall.app.page.canvas.CanvasProductSku;
import com.shop.zkmall.app.page.canvas.CanvasProductUsers;
import com.shop.zkmall.app.page.cart.CartSku;
import com.shop.zkmall.app.page.index.Product;
import com.shop.zkmall.app.page.product.*;
import com.shop.zkmall.app.page.settlement.Distribution;
import com.shop.zkmall.app.param.canvas.CanvasAppProductParam;
import com.shop.zkmall.app.param.index.SearchParam;
import com.shop.zkmall.app.param.product.PageProductParam;
import com.shop.zkmall.app.param.product.ProductParam;
import com.shop.zkmall.app.param.product.SkuParam;
import com.shop.zkmall.app.service.buyer.CereBuyerCommentLikeService;
import com.shop.zkmall.app.service.buyer.CereBuyerReceiveService;
import com.shop.zkmall.app.service.collect.CereBuyerCollectService;
import com.shop.zkmall.app.service.collect.CereBuyerFootprintService;
import com.shop.zkmall.app.service.logistics.CereOrderLogisticsService;
import com.shop.zkmall.app.service.order.CereShopOrderService;
import com.shop.zkmall.app.service.product.CereCommentWordService;
import com.shop.zkmall.app.service.product.CereProductSkuService;
import com.shop.zkmall.app.service.product.CereShopProductService;
import com.shop.zkmall.app.service.shop.CerePlatformShopService;
import com.shop.zkmall.app.service.shop.CereShopCommentService;
import com.shop.zkmall.app.service.shop.CereShopVisitService;
import com.shop.zkmall.app.service.stock.CereStockService;
import com.shop.zkmall.app.utils.AlipayUtil;
import com.shop.zkmall.app.utils.CacheClient;
import com.shop.zkmall.app.utils.RedisConstants;
import com.shop.zkmall.app.utils.WechatUtil;
import com.shop.zkmall.commons.cache.constants.CacheKeyConstants;
import com.shop.zkmall.commons.cache.product.ProductBo;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.constant.IntegerEnum;
import com.shop.zkmall.commons.domain.buyer.CereBuyerCommentLike;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.collect.CereBuyerCollect;
import com.shop.zkmall.commons.domain.collect.CereBuyerFootprint;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.logistics.CereOrderLogistics;
import com.shop.zkmall.commons.domain.product.*;
import com.shop.zkmall.commons.domain.shop.CereShopVisit;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.result.Result;
import com.shop.zkmall.commons.utils.EmptyUtils;
import com.shop.zkmall.commons.utils.QRCodeUtil;
import com.shop.zkmall.commons.utils.SpringUtil;
import com.shop.zkmall.commons.utils.TimeUtils;
import com.shop.zkmall.oss.FileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zkmall zkmallvip@163.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CereShopProductServiceImpl implements CereShopProductService {

    private final CereShopProductDAO cereShopProductDAO;

    private final CereBuyerReceiveService cereBuyerReceiveService;

    private final CereShopCommentService cereShopCommentService;

    private final CereShopVisitService cereShopVisitService;

    private final CereProductSkuService cereProductSkuService;

    private final CereCommentWordService cereCommentWordService;

    private final CereBuyerFootprintService cereBuyerFootprintService;

    private final CereBuyerCommentLikeService cereBuyerCommentLikeService;

    private final FileStrategy fileStrategy;

    private final RedisTemplate<String, Object> redisTemplate;

    private final CereStockService cereStockService;

    private final CereOrderLogisticsService cereOrderLogisticsService;

    private final CereShopOrderService cereShopOrderService;

    private final CereProductStatsByDayDAO cereProductStatsByDayDAO;

    private final CereSkuMemberRealInfoDAO cereSkuMemberRealInfoDAO;

    private final CereProductParameterDAO cereProductParameterDAO;

    private final AlipayUtil alipayUtil;

    private final ObjectMapper objectMapper;

    private final StringRedisTemplate stringRedisTemplate;

    private final CacheClient cacheClient;

    /**
     * 商品详情前缀
     */
    @Value("${goods_url}")
    private String goodsUrl;

    /**
     * 商品缓存时间
     */
    @Value("${productCacheTime}")
    private long productCacheTime;

    @Value("${defaultHeadImg}")
    private String defaultHeadImg;

    @Value("${querySkuRealInfo}")
    private boolean querySkuRealInfo;

    @Override
    public CereShopProduct selectByPrimaryKey(Long id) {
        return cereShopProductDAO.selectById(id);
    }

    //我的实现
    @Override
    public ProductDetail getById(ProductParam param, CereBuyerUser user) throws Exception {
        ProductDetail detail;

        if (querySkuRealInfo) {
            Long skuId = param.getSkuId();
            Long shopId = param.getShopId();
            Long memberLevelId = user != null && user.getMemberLevelId() != null ? user.getMemberLevelId() : 0L;
            //是热点sku，用redis
            if (MyXxlJobHandler.top1000HotSkuIdsSet != null && MyXxlJobHandler.top1000HotSkuIdsSet.contains(param.getSkuId())) {

                detail = cacheClient.queryWithMutex(
                        RedisConstants.SKU_ID_REAL_INFO,
                        skuId,
                        ProductDetail.class,
                        id -> cereSkuMemberRealInfoDAO.findProductDetailBySkuId(id, shopId, memberLevelId),
                        3600L, TimeUnit.MINUTES);
            } else {
                detail = cacheClient.queryWithPassThrough(
                        RedisConstants.SKU_ID_REAL_INFO,
                        skuId,
                        ProductDetail.class,
                        id -> cereSkuMemberRealInfoDAO.findProductDetailBySkuId(id, shopId, memberLevelId),
                        3600L, TimeUnit.MINUTES);

            }
        } else {
            Long skuId = param.getSkuId();

//            if (shopId == null) {
//                CereShopProduct cereShopProduct = cereShopProductDAO.selectById(param.getProductId());
//                if (cereShopProduct==null) {
//                    throw new CoBusinessException(CoReturnFormat.SYS_ERROR,"商品不存在");
//                }
//                shopId=cereShopProduct.getShopId();
//            }
            Long shopId = param.getShopId();
            if (MyXxlJobHandler.top1000HotSkuIdsSet != null && MyXxlJobHandler.top1000HotSkuIdsSet.contains(param.getSkuId())) {

                detail = cacheClient.queryWithMutex(
                        RedisConstants.SKU_ID_REAL_INFO,
                        skuId,
                        ProductDetail.class,
                        id -> cereShopProductDAO.findBySkuId(id, shopId),
                        60L, TimeUnit.MINUTES);
            } else {
                detail = cacheClient.queryWithPassThrough(
                        RedisConstants.SKU_ID_REAL_INFO,
                        skuId,
                        ProductDetail.class,
                        id -> cereShopProductDAO.findBySkuId(id, shopId),
                        60L, TimeUnit.MINUTES);

            }


        }
        if (detail != null) {
            if (querySkuRealInfo) {
                detail = assembleActivityInfoForRealInfo(detail, user, param);
            } else {
                detail = assembleActivityInfo(detail, user, param);
            }


            Long totalStockNumber = cacheClient.queryWithMutex(
                    RedisConstants.TOTAL_STOCK_PRODUCT,
                    param.getProductId(),
                    Long.class,
                    cereProductSkuService::totalStockNumber,
                    5L,
                    TimeUnit.MINUTES);
            detail.setTotalStockNumber(totalStockNumber);
            detail.setImages(cereShopProductDAO.findImages(param.getProductId()));
            if (user != null) {
                detail.setReceive(cereBuyerReceiveService.findlatelyReceiveByUserId(user.getBuyerUserId()));
                if (detail.getReceive() != null && IntegerEnum.YES.getCode().equals(detail.getIfLogistics())
                        && IntegerEnum.YES.getCode().equals(detail.getSceneFreeShipping())) {
                    List<CereOrderLogistics> logistics = cereOrderLogisticsService.findLogistics(detail.getShopId());

                    HashMap<Long, Integer> numberMap = new HashMap<>();
                    numberMap.put(detail.getSkuId(), 1);

                    List<CartSku> skuList = new ArrayList<>();
                    CartSku sku = new CartSku();
                    sku.setSkuId(detail.getSkuId());
                    sku.setIfLogistics(IntegerEnum.YES.getCode());
                    sku.setWeight(detail.getWeight());
                    skuList.add(sku);

                    Distribution distribution = cereShopOrderService.setLogisticPrice(logistics, skuList, detail.getReceive(), numberMap);
                    if (distribution != null) {
                        detail.setLogisticsPrice(distribution.getDistributionPrice());
                    }
                }

                CereBuyerCollectService cereBuyerCollectService = SpringUtil.getBean(CereBuyerCollectService.class);
                CereBuyerCollect collect = cereBuyerCollectService.findByUserProduct(user.getBuyerUserId(), detail.getProductId());
                if (collect != null) {
                    detail.setIfCollect(collect.getState());
                    detail.setCollectId(collect.getCollectId());
                }
            }
            List<SkuName> names = cereProductSkuService.findSkuNames(param.getProductId());
            if (!EmptyUtils.isEmpty(names)) {
                //规格值
                List<SkuValue> values = cereProductSkuService.findValuesByProductId(param.getProductId());
                if (!EmptyUtils.isEmpty(values)) {
                    names.forEach(name -> {
                        if (!EmptyUtils.isEmpty(name.getNameCode())) {
                            List<SkuValue> skuValues = new ArrayList<>();
                            for (int i = 0; i < values.size(); i++) {
                                if (i < 0) {
                                    i = 0;
                                }
                                if (values.get(i).getSkuName().equals(name.getSkuName())) {
                                    skuValues.add(values.get(i));
                                    values.remove(i);
                                    i--;
                                }
                            }
                            name.setValues(skuValues);
                        }

                    });
                }

            }
            detail.setNames(names);
            //查询商品评论信息
            List<BuyerComment> comments = cereShopCommentService.findByProductId(param.getProductId());
            //封装评论图片数组数据
            if (!EmptyUtils.isEmpty(comments)) {
                comments.forEach(buyerComment -> {
                    buyerComment.setImages(EmptyUtils.getImages(buyerComment.getImage()));
                    buyerComment.setAddImages(EmptyUtils.getImages(buyerComment.getAddImage()));
                    buyerComment.setValues(EmptyUtils.getImages(buyerComment.getValue()));
                    //设置是否点赞
                    if (user != null) {
                        CereBuyerCommentLike cereBuyerCommentLike = cereBuyerCommentLikeService.findByUserIdAndCommentId(user.getBuyerUserId(), buyerComment.getCommentId());
                        if (cereBuyerCommentLike != null) {
                            buyerComment.setIfLike(cereBuyerCommentLike.getIfLike());
                        }
                    }
                });
                //查询该商品评论关联关键词统计数据
                List<CommentWord> words = cereCommentWordService.findByProductId(param.getProductId());
                detail.setWords(words);
            }
            detail.setComments(comments);
            //查询店铺商品总类数
            detail.setClassifyNumber(cereShopProductDAO.findClassifyNumber(param.getShopId()).size());
            //查询同类商品
            List<Product> similarProducts = cereShopProductDAO.findSimilarProducts(detail.getClassifyId());
            detail.setSimilarProducts(similarProducts);
            //查询商品参数
            String time = TimeUtils.yyMMddHHmmss();
            detail.setParameterList(cereProductParameterDAO.selectByProductId(detail.getProductId()));
           /* if (user != null) {
                //新增访问记录
                CereShopVisit visit = new CereShopVisit();
                visit.setBuyerUserId(user.getBuyerUserId());
                visit.setShopId(detail.getShopId());
                visit.setVisitTime(time);
                visit.setTerminal(param.getTerminal());
                visit.setSystem(param.getSystem());
                visit.setCity(param.getCity());
                cereShopVisitService.insert(visit);
                //新增足迹
                CereBuyerFootprint cereBuyerFootprint = new CereBuyerFootprint();
                cereBuyerFootprint.setBuyerUserId(user.getBuyerUserId());
                cereBuyerFootprint.setProductId(detail.getProductId());
                cereBuyerFootprint.setCreateTime(time);
                cereBuyerFootprint.setShopId(detail.getShopId());
                cereBuyerFootprint.setSkuId(detail.getSkuId());
                cereBuyerFootprint.setSelected(IntegerEnum.NO.getCode());
                cereBuyerFootprintService.insert(cereBuyerFootprint);
            }
            //更新日度统计
            CereProductStatsByDay stats = new CereProductStatsByDay();
            stats.setShopId(detail.getShopId());
            stats.setCreateDate(TimeUtils.today());
            stats.setProductId(detail.getProductId());
            stats.setAddCartCount(0);
            stats.setVisitCount(1);
            stats.setSalesVolume(0);
            cereProductStatsByDayDAO.insertOrUpdate(stats);
*/
            //更新热点规格id缓存
            stringRedisTemplate.opsForZSet().incrementScore(
                    RedisConstants.HOT_SKU + LocalDate.now(), param.getSkuId().toString(), 1);

//            log.info("getById,skuId:" + param.getSkuId() + "放入了redis," +
//                    "key=" + RedisConstants.HOT_SKU + LocalDate.now() +
//                    "value=" + param.getSkuId().toString());

        }


        return detail;
    }


    /**
     * 查询商品详情
     */
    private ProductDetail queryProductDetail(Long skuId, Long memberLevelId) {
        ProductDetail detail = cereShopProductDAO.queryProductDetail(skuId);
        if (detail == null) {
            return null;
        }
        //查询付款人数
        detail.setUsers(cereShopOrderService.findPayUsers(detail.getProductId(), null));
        memberLevelId = Optional.ofNullable(memberLevelId).orElse(0L);
        CereSkuMemberRealInfo skuMemberRealRealInfo = cereSkuMemberRealInfoDAO.selectOne(Wrappers.<CereSkuMemberRealInfo>lambdaQuery()
                .eq(CereSkuMemberRealInfo::getSkuId, skuId)
                .eq(CereSkuMemberRealInfo::getMemberLevelId, memberLevelId));
        if (skuMemberRealRealInfo != null) {
            detail.setActivityType(skuMemberRealRealInfo.getActivityType());
            detail.setActivityName(IntegerEnum.getActivityName(skuMemberRealRealInfo.getActivityType()));
            detail.setLimitNumber(skuMemberRealRealInfo.getLimitNumber());
        } else {
            //下架的情况，没有skuRealInfo
            detail.setPrice(BigDecimal.ZERO);
            detail.setOriginalPrice(BigDecimal.ZERO);
            detail.setActivityType(IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode());
            detail.setActivityName(IntegerEnum.ACTIVITY_TYPE_NORMAL.getName());
            detail.setLimitNumber(0);
        }
        return detail;
    }

    /**
     * 组装商品活动信息
     */
    private ProductDetail assembleActivityInfo(ProductDetail detail, CereBuyerUser user, ProductParam param) {
        //其它活动可能已经设置了该参数，所以这里做一下null判断
        if (detail.getMap() == null) {
            //查询该商品所有组合规格数据封装到map
            List<Sku> skus = cereProductSkuService.findSimpleSkuByProductId(param.getProductId());
            if (!EmptyUtils.isEmpty(skus)) {
                Map<String, Sku> map = new HashMap<>();
                String image = detail.getImage();
                skus.forEach(sku -> {
                    if (EmptyUtils.isEmpty(sku.getImage())) {
                        sku.setImage(image);
                    }
                    //查询规格所有规格值拼接字符串
                    if (EmptyUtils.isEmpty(sku.getValueCodes())) {
                        sku.setValueCodes("单款项");
                    }
                    map.put(sku.getValueCodes(), sku);
                });
                detail.setMap(map);
            }
        }

        //total和surplusNumber兜底逻辑
        if (detail.getTotal() == null || detail.getSurplusNumber() == null) {
            ProductStockInfo stockInfo = cereProductSkuService.findProductStockInfo(detail.getProductId());
            if (stockInfo != null) {
                if (detail.getTotal() == null) {
                    detail.setTotal(stockInfo.getTotal());
                }
                if (detail.getSurplusNumber() == null) {
                    detail.setSurplusNumber(stockInfo.getStockNumber());
                }
            }
        }

        return detail;
    }

    /**
     * 组装商品活动信息
     */
    private ProductDetail assembleActivityInfoForCache(ProductDetail detail, CereBuyerUser user, ProductParam param) {
        //其它活动可能已经设置了该参数，所以这里做一下null判断
        if (detail.getMap() == null) {
            //查询该商品所有组合规格数据封装到map
            List<Sku> skus = cereProductSkuService.findSimpleSkuByProductId(param.getProductId());
            if (!EmptyUtils.isEmpty(skus)) {
                Map<String, Sku> map = new HashMap<>();
                String image = detail.getImage();
                skus.forEach(sku -> {
                    if (EmptyUtils.isEmpty(sku.getImage())) {
                        sku.setImage(image);
                    }
                    //查询规格所有规格值拼接字符串
                    if (EmptyUtils.isEmpty(sku.getValueCodes())) {
                        sku.setValueCodes("单款项");
                    }
                    map.put(sku.getValueCodes(), sku);
                });
                detail.setMap(map);
            }
        }

        //total和surplusNumber兜底逻辑
        if (detail.getTotal() == null || detail.getSurplusNumber() == null) {
            ProductStockInfo stockInfo = cereProductSkuService.findProductStockInfo(detail.getProductId());
            if (stockInfo != null) {
                if (detail.getTotal() == null) {
                    detail.setTotal(stockInfo.getTotal());
                }
                if (detail.getSurplusNumber() == null) {
                    detail.setSurplusNumber(stockInfo.getStockNumber());
                }
            }
        }

        return detail;
    }

    @Override
    public ProductSkus getProducts(SkuParam param) {
        //查询该商品所有规格名
        List<SkuName> names = cereProductSkuService.findSkuNames(param.getProductId());
        if (!EmptyUtils.isEmpty(names)) {
            if (!EmptyUtils.isEmpty(names.get(0).getSkuName())) {
                //多款式
                names.forEach(name -> name.setValues(cereProductSkuService.findSkuValues(name.getSkuName(), param.getProductId())));
            } else {
                //单款式
                names.get(0).setValues(cereProductSkuService.findOneValues(param.getSkuId()));
            }
        }
        //查询当前商品第一条规格数据
        ProductSkus productSkus;
        if (!EmptyUtils.isEmpty(param.getSkuId())) {
            productSkus = cereProductSkuService.findSku(param.getSkuId());
        } else {
            productSkus = cereProductSkuService.findFirstSku(param.getProductId());
        }
        if (productSkus != null) {
            productSkus.setNames(names);
            //查询活动数据
        }
        //查询该商品所有组合规格数据封装到map
        List<Sku> skus = cereProductSkuService.findSkuByProductId(param.getProductId());
        if (!EmptyUtils.isEmpty(skus)) {
            Map<String, Sku> map = new HashMap<>();
            ProductSkus finalProductSkus = productSkus;
            skus.forEach(sku -> {

                if (EmptyUtils.isEmpty(sku.getImage())) {
                    sku.setImage(finalProductSkus.getImage());
                }
                //查询规格所有规格值拼接字符串
                String key = cereProductSkuService.findValueBySkuId(sku.getSkuId());
                if (EmptyUtils.isEmpty(key)) {
                    key = "单款项";
                }
                map.put(key, sku);
            });
            productSkus.setMap(map);
        }
        return productSkus;
    }

    @Override
    public List<Product> getSearchProducts(SearchParam param) {
        return cereShopProductDAO.getSearchProducts(param);
    }

    @Override
    public Page<CanvasProduct> getCanvasProducts(CanvasAppProductParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CanvasProduct> list;
        if (querySkuRealInfo) {
            param.setMemberLevelId(Optional.ofNullable(param.getMemberLevelId()).orElse(0L));
            list = cereShopProductDAO.getCanvasActivityRealInfo(param);
        } else {
            list = cereShopProductDAO.getCanvasProducts(param);
            if (!EmptyUtils.isEmpty(list)) {
                for (CanvasProduct product : list) {
                    ProductBo productBo = fetchProductCache(product.getShopId(), product.getProductId(), product.getSkuId(), null, true);
                    if (productBo != null) {
                        product.setPrice(productBo.getPrice());
                        product.setOriginalPrice(productBo.getOriginalPrice());
                        product.setActivityType(productBo.getActivityType());
                        product.setIfEnable(productBo.getIfEnable());
                        product.setTime(productBo.getTime());
                        product.setStockNumber(productBo.getSurplusNumber());
                    }
                }
            }
        }

        PageInfo<CanvasProduct> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public Page<CanvasProduct> getProducts2(CanvasAppProductParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CanvasProduct> list;
        Map<Long, CanvasProductUsers> productUsersMap = new HashMap<>();
        Map<Long, CanvasProductSku> productSkuMap = new HashMap<>();
        Map<Long, CanvasProductNumber> productNumberMap = new HashMap<>();
        if (querySkuRealInfo) {
            param.setMemberLevelId(Optional.ofNullable(param.getMemberLevelId()).orElse(0L));
            list = cereShopProductDAO.getCanvasActivityRealInfo(param);
        } else {
            if (IntegerEnum.CANVAS_ACTIVITY_GROUP_WORK.getCode().equals(param.getType())) {
                list = Collections.emptyList();
            } else if (IntegerEnum.CANVAS_ACTIVITY_SECKILL.getCode().equals(param.getType())) {
                list = Collections.emptyList();
            } else if (IntegerEnum.CANVAS_ACTIVITY_DISCOUNT.getCode().equals(param.getType())) {
                list = Collections.emptyList();
            } else {
                list = cereShopProductDAO.getCanvasProducts2(param);
                //补充查询产品付款人数
                productUsersMap = cereShopProductDAO.getProductUsers(param);
                //补充查询产品sku信息
                productSkuMap = cereShopProductDAO.getProductSku(param);
                //补充查询产品购买数据
                productNumberMap = cereShopProductDAO.getProductNumber(param);
            }
            //填充产品属性
            fillProductProperties(list, productUsersMap, productSkuMap, productNumberMap);
        }

        PageInfo<CanvasProduct> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    private void fillProductProperties(List<CanvasProduct> list, Map<Long, CanvasProductUsers> productUsersMap, Map<Long, CanvasProductSku> productSkuMap, Map<Long, CanvasProductNumber> productNumberMap) {

        boolean usersMapIsEmpty = productUsersMap.isEmpty();
        boolean skuMapIsEmpty = productSkuMap.isEmpty();
        boolean numberMapIsEmpty = productNumberMap.isEmpty();

        if (!EmptyUtils.isEmpty(list)) {
            for (CanvasProduct product : list) {
                Long productId = product.getProductId();
                if (!usersMapIsEmpty) {
                    //填充产品购买人数信息
                    product.setUsers(productUsersMap.get(productId).getUsers());
                }
                if (!skuMapIsEmpty) {
                    //填充产品sku信息
                    CanvasProductSku canvasProductSku = productSkuMap.get(productId);
                    product.setSkuId(canvasProductSku.getSkuId());
                    product.setImage(canvasProductSku.getImage());
                    product.setOriginalPrice(canvasProductSku.getOriginalPrice());
                    product.setPrice(canvasProductSku.getPrice());
                    product.setStockNumber(canvasProductSku.getStockNumber());
                }
                if (!numberMapIsEmpty) {
                    //填充产品销量信息
                    product.setNumber(productNumberMap.get(productId).getNumber());
                }

                ProductBo productBo = fetchProductCache(product.getShopId(), productId, product.getSkuId(), null, true);

                if (productBo != null) {
                    product.setPrice(productBo.getPrice());
                    product.setOriginalPrice(productBo.getOriginalPrice());
                    product.setActivityType(productBo.getActivityType());
                    product.setIfEnable(productBo.getIfEnable());
                    product.setTime(productBo.getTime());
                    product.setStockNumber(productBo.getSurplusNumber());
                }

            }
        }
    }


    @Override
    public List<Classify> getClassify() {
        //查询所有一级类目
        List<Classify> list = cereShopProductDAO.findAllClassify();
        //查询所有子节点类目
        List<Classify> childs = cereShopProductDAO.findChildsClassify();
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<>();
            list.forEach((classify -> setChildsIndex(classify, childs, map)));
        }
        return list;
    }

    @Override
    public List<Classify> getClassifyByIds(List<Long> ids) {
        //查询所有一级类目
        List<Classify> list = cereShopProductDAO.findAllClassify();
        //查询所有子节点类目
        List<Classify> childs = cereShopProductDAO.findChildsClassify();
        if (!EmptyUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<>();
            list.forEach((classify -> setChildsIndex(classify, childs, map)));
        }
        //TODO 需要优化
        return list.stream().filter(obj -> ids.contains(obj.getId())).collect(Collectors.toList());
    }

    @Override
    public void deleteAllSearch(CereBuyerUser user) {
        if (user != null) {
            cereShopProductDAO.deleteAllSearch(user.getBuyerUserId());
        }
    }

    @Override
    public List<Long> findClassifyNumber(Long shopId) {
        return cereShopProductDAO.findClassifyNumber(shopId);
    }

    @Override
    public InputStream getSharePic(ProductParam param, CereBuyerUser user) throws Exception {
        if (user == null) {
            throw new CoBusinessException(CoReturnFormat.USER_NOT_LOGIN);
        }
        ProductDetail detail = getById(param, user);
        int env = param.getTerminal();
        String itemPicUrl = CollectionUtils.isNotEmpty(detail.getImages()) ? detail.getImages().get(0) : "";
        String shareHeadUrl = user.getHeadImage();
        if (StringUtils.isBlank(shareHeadUrl)) {
            shareHeadUrl = defaultHeadImg;
        }
        String shareName = user.getName();
        if (StringUtils.isBlank(shareName)) {
            shareName = user.getWechatName();
        }
        String itemName = detail.getProductName();
        Double itemPrice = detail.getPrice().doubleValue();
        Long distributorId = null;
        String itemUrlSuffix = "?shopId=" + param.getShopId() + "&productId=" + param.getProductId()
                + "&skuId=" + param.getSkuId() + "&salesId=" + distributorId + "&shopGroupWorkId=" + detail.getShopGroupWorkId();
        String accessToken = WechatUtil.getAccessToken();
        return genPoster(env, itemPicUrl, shareHeadUrl, shareName, itemName, itemPrice, itemUrlSuffix, accessToken);
    }

    @Override
    public Page<Product> getRandomSortProduct(PageProductParam param, Long buyerUserId) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Product> list = cereShopProductDAO.getRandomSortProduct(param);
        CerePlatformShopService cerePlatformShopService = SpringUtil.getBean(CerePlatformShopService.class);
        if (!EmptyUtils.isEmpty(list)) {
            list.forEach(a -> {
                //查询付款人数
                a.setUsers(cerePlatformShopService.findPayUsers(a.getProductId()));
                //设置活动信息
                ProductBo productBo = fetchProductCache(a.getShopId(), a.getProductId(), a.getSkuId(), null, false);
                if (productBo != null) {
                    a.setActivityType(productBo.getActivityType());
                    a.setOriginalPrice(productBo.getOriginalPrice());
                    a.setPrice(productBo.getPrice());
                    a.setTotal(productBo.getTotal());
                }
            });
        }

        PageInfo<Product> pageInfo = new PageInfo<>(list);
        return new Page<>(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 查询skuId对应的缓存
     */
    @Override
    public ProductBo fetchProductCache(Long shopId, Long productId, Long skuId, CereBuyerUser user, boolean needRealtimeStock) {
        if (productId == null || productId <= 0) {
            return null;
        }
        //log.info("fetchProductCache shopId = {}, productId = {}, skuId = {}, needRealtimeStock = {}", shopId, productId, skuId, needRealtimeStock);
        String cacheKey = CacheKeyConstants.SKU_CACHE_PREFIX + "_" + productId + "_" + skuId;
        String productStr = (String) redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(productStr)) {
            try {
                ProductBo productBo = objectMapper.readValue(productStr, ProductBo.class);
                //缓存没有超过配置的时间，才可以返回，否则需要重新查询
                if (System.currentTimeMillis() - productBo.getLastUpdateTimestamp() <= productCacheTime) {
                    if (needRealtimeStock) {
                        assembleStock(productBo, skuId);
                    }
                    //log.info("fetchProductCache byCache productId = {}, skuId = {}, use time {} mills", productId, skuId, System.currentTimeMillis() - start);
                    return productBo;
                }
            } catch (Exception e) {
                log.error("获取[{}-{}-{}]商品缓存信息错误:", shopId, productId, skuId, e);
            }
        }
        ProductParam param = new ProductParam();
        param.setShopId(shopId);
        param.setProductId(productId);
        param.setSkuId(skuId);
        ProductDetail detail = cereShopProductDAO.findBySkuId(param.getSkuId(), param.getShopId());
        if (detail == null) {
            return null;
        }
        try {
            detail = assembleActivityInfoForCache(detail, user, param);
            ProductBo productBo = new ProductBo();
            BeanUtils.copyProperties(detail, productBo);
            productBo.setLastUpdateTimestamp(System.currentTimeMillis());
            redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(productBo));
            //log.info("fetchProductCache byDb productId = {}, skuId = {}, use time {} mills", productId, skuId, System.currentTimeMillis() - start);
            return productBo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Product> getSimpleActivityInfo(Long shopId, List<Long> skuIdList, CereBuyerUser user) {
        if (shopId == null || CollectionUtils.isEmpty(skuIdList)) {
            return Collections.emptyMap();
        }
        //拼团、平台秒杀、平台折扣、商家秒杀、商家折扣 状态和价格都是对应的
        List<Product> productDetailList = cereShopProductDAO.findByShopIdAndSkuIdList(shopId, skuIdList);

        /*List<Product> normalProductList = productDetailList.stream().filter(obj
                -> IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(obj.getActivityType())).collect(Collectors.toList());
        if (user != null) {
            cereShopSceneService.batchSetActivityInfo(user, normalProductList);

            normalProductList = normalProductList.stream().filter(obj
                    -> IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode().equals(obj.getActivityType())).collect(Collectors.toList());
            cereProductMemberService.batchSetActivityInfo(user, normalProductList);
        }*/
        productDetailList.parallelStream().forEach(obj -> {
            obj.setPrice(obj.getPrice().setScale(2, RoundingMode.HALF_UP));
            obj.setOriginalPrice(obj.getOriginalPrice().setScale(2, RoundingMode.HALF_UP));
        });
        return productDetailList.stream().collect(Collectors.toMap(Product::getSkuId, Function.identity()));
    }

    @Override
    public int selectFictitiousNumber(Long productId) {
        return cereShopProductDAO.selectFictitiousNumber(productId);
    }

    @Override
    public ProductSkus getSpecificSku(SkuParam param) {
        //查询该商品所有规格名
        List<SkuName> names = cereProductSkuService.findSkuNames(param.getProductId());
        if (!EmptyUtils.isEmpty(names)) {
            if (!EmptyUtils.isEmpty(names.get(0).getSkuName())) {
                //多款式
                names.forEach(name -> name.setValues(cereProductSkuService.findSkuValueBySkuId(name.getSkuName(), param.getSkuId())));
            } else {
                //单款式
                names.get(0).setValues(cereProductSkuService.findOneValues(param.getSkuId()));
            }
        }
        //查询当前商品第一条规格数据
        ProductSkus productSkus;
        if (!EmptyUtils.isEmpty(param.getSkuId())) {
            productSkus = cereProductSkuService.findSku(param.getSkuId());
        } else {
            productSkus = cereProductSkuService.findFirstSku(param.getProductId());
        }
        if (productSkus != null) {
            productSkus.setNames(names);
        }
        //查询该商品所有组合规格数据封装到map
        List<Sku> skus = cereProductSkuService.findSkuByProductId(param.getProductId());
        if (!EmptyUtils.isEmpty(skus)) {
            Map<String, Sku> map = new HashMap<>();
            ProductSkus finalProductSkus = productSkus;
            //定义是否活动数据
            skus.forEach(sku -> {

                if (EmptyUtils.isEmpty(sku.getImage())) {
                    sku.setImage(finalProductSkus.getImage());
                }
                //查询规格所有规格值拼接字符串
                String key = cereProductSkuService.findValueBySkuId(sku.getSkuId());
                if (EmptyUtils.isEmpty(key)) {
                    key = "单款项";
                }
                map.put(key, sku);
            });
            productSkus.setMap(map);
        }
        return productSkus;
    }

    @Override
    public List<BroadCastDTO> getBroadCastList(Long productId, Long shopGroupWorkId, Long buyerUserId) {
        try {
            String oneHourAgo = TimeUtils.getMoreHourAfter(TimeUtils.yyMMddHHmmss(), -1);
            List<BroadCastDTO> list = cereShopCommentService.findRecentComment(productId, oneHourAgo);
            /*if (shopGroupWorkId != null) {
                List<BroadCastDTO> list2 = cereCollageOrderService.findRecentCollageOrderGoing(oneHourAgo, shopGroupWorkId, productId);
                List<BroadCastDTO> list3 = cereCollageOrderService.findRecentCollageOrderDone(oneHourAgo, shopGroupWorkId, productId);
                list.addAll(list2);
                list.addAll(list3);
            } else {*/
            List<BroadCastDTO> list4 = cereShopOrderService.findRecentOrder(oneHourAgo, productId);
            list.addAll(list4);
            //}

            list.sort(Comparator.comparing(BroadCastDTO::getTime));

            list.forEach(obj -> {
                obj.setName(com.shop.zkmall.commons.utils.StringUtils.encodeInfo(obj.getName()));
                obj.setTimeStr(generateTimeStr(obj.getTime()));
            });
            return list;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private String generateTimeStr(String time) {
        if (StringUtils.isBlank(time)) {
            return "几分钟前";
        }
        Date date = DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss");
        long mills = System.currentTimeMillis() - date.getTime();
        long secs = mills / 1000;
        long mins = secs / 60;
        if (mins == 0) {
            if (secs == 0) {
                return "刚刚";
            } else {
                return secs + "秒前";
            }
        } else {
            return mins + "分钟前";
        }
    }

    @Override
    public ShareProduct getShareProductParam(ProductParam param, CereBuyerUser user) throws Exception {
        if (user == null) {
            throw new CoBusinessException(CoReturnFormat.USER_NOT_LOGIN);
        }
        int env = param.getTerminal();
        String shareHeadUrl = user.getHeadImage();
        if (StringUtils.isBlank(shareHeadUrl)) {
            shareHeadUrl = defaultHeadImg;
        }
        String shareName = user.getName();
        if (StringUtils.isBlank(shareName)) {
            shareName = user.getWechatName();
        }
        Long distributorId = null;
        String itemUrlSuffix = "?shopId=" + param.getShopId() + "&productId=" + param.getProductId() + "&skuId=" + param.getSkuId() + "&salesId=" + distributorId;
        String accessToken = null;
        if (env == 1 || env == 2) {
            accessToken = WechatUtil.getAccessToken();
        }
        String qrCodeBase64 = genPosterSimple(env, itemUrlSuffix, accessToken);
        ShareProduct result = new ShareProduct();
        result.setHeadImage(shareHeadUrl);
        result.setName(shareName);
        result.setShareImage(qrCodeBase64);
        return result;
    }

    @Override
    public Page<CanvasProduct> selectRecommendProductList(Long buyerUserId, CanvasAppProductParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        //TODO 判断非querySkuRealInfo的情况
        if (querySkuRealInfo) {
            param.setMemberLevelId(Optional.ofNullable(param.getMemberLevelId()).orElse(0L));
            List<CanvasProduct> list = cereShopProductDAO.getRecommendActivityRealInfo(param);
            PageInfo<CanvasProduct> pageInfo = new PageInfo<>(list);
            return new Page<>(pageInfo.getList(), pageInfo.getTotal());
        }
        return null;
    }

    /**
     * 库存变化频繁，需要重新查数据库
     */
    private void assembleStock(ProductBo productBo, Long skuId) {
        if (productBo.getMap() != null) {
            boolean useSameStock = false;
            Integer total = 0;
            Integer stockNumber = 0;
            productBo.setTotal(null);
            productBo.setSurplusNumber(null);
            Long productId = productBo.getProductId();
            for (Sku sku : productBo.getMap().values()) {
                if (useSameStock) {
                    sku.setTotal(total);
                    sku.setStockNumber(stockNumber);
                    continue;
                }
                Integer activityType = sku.getActivityType();
            }
            //上面的for循环，没有查到相应的库存信息
            if (productBo.getTotal() == null || productBo.getSurplusNumber() == null) {
                boolean hasNull = false;
                Integer sumTotal = 0;
                Integer sumStockNumber = 0;
                for (Sku sku : productBo.getMap().values()) {
                    ProductStockInfo skuStockInfo = cereStockService.getSkuStockInfo(sku.getSkuId());
                    if (skuStockInfo != null) {
                        sku.setTotal(skuStockInfo.getTotal());
                        sku.setStockNumber(skuStockInfo.getStockNumber());
                        sumTotal += sku.getTotal();
                        sumStockNumber += sku.getStockNumber();
                    } else {
                        hasNull = true;
                        break;
                    }
                }
                //缓存查不到，就到数据库去查
                if (hasNull) {
                    sumTotal = 0;
                    sumStockNumber = 0;
                    //这里只需要用到stock_number 和 total
                    List<Sku> skuList = cereProductSkuService.findStockNumberByProductId(productBo.getProductId());
                    Map<Long, Sku> skuMap = new HashMap<>();

                    for (Sku sku : skuList) {
                        skuMap.put(sku.getSkuId(), sku);
                        sumTotal += sku.getTotal();
                        sumStockNumber += sku.getStockNumber();
                    }
                    for (Sku sku : productBo.getMap().values()) {
                        Sku querySku = skuMap.get(sku.getSkuId());
                        if (querySku != null) {
                            sku.setTotal(querySku.getTotal());
                            sku.setStockNumber(querySku.getStockNumber());
                            //更新缓存
                            cereStockService.updateSkuStock(sku.getSkuId(), querySku.getTotal(), querySku.getStockNumber());
                        }
                    }
                }

                productBo.setTotal(sumTotal);
                productBo.setSurplusNumber(sumStockNumber);
            }
        }
    }

    private Classify setChildsIndex(Classify parent, List<Classify> all, Map<String, Integer> map) throws ArrayIndexOutOfBoundsException {
        if (!parent.getParentId().equals(0L)) {
            //回调进来的,设置回调执行次数+1
            map.put("callback", map.get("callback") + 1);
        } else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback", 0);
        }
        List<Classify> childs = new ArrayList<>();
        if (!EmptyUtils.isEmpty(all)) {
            for (int i = 0; i < all.size(); i++) {
                if (!all.isEmpty()) {
                    if (i < 0) {
                        i = 0;
                    }
                    Classify classify = all.get(i);
                    //设置按钮菜单权限
                    if (parent.getId().equals(classify.getParentId())) {
                        all.remove(i);
                        i--;
                        //执行回调
                        Classify itemPermission = setChildsIndex(classify, all, map);
                        childs.add(itemPermission);
                        //判断当前是否回到最高级菜单节点
                        if (parent.getParentId().equals(0L)) {
                            //如果是,计算索引值=当前索引值-（回调执行次数-1）
                            i = i - (map.get("callback") - 1);
                            //初始化回调执行次数
                            map.put("callback", 0);
                        }
                    }
                }
            }
            parent.setChilds(childs);
        }
        return parent;
    }

    private String genPosterSimple(int env, String itemUrlSuffix, String accessToken) throws Exception {
        BufferedImage qrCode = null;
        String result = null;
        if (env == 1 || env == 2) {
            qrCode = WechatUtil.getWxacode(accessToken, "zwx_category_page1/goodsModule/goodsDetails" + itemUrlSuffix);
        } else if (env == 3) {
            qrCode = QRCodeUtil.createImage(goodsUrl + itemUrlSuffix, null, true);
        } else if (env == 4) {
            String qrCodeUrl = alipayUtil.generateQrCode("zwx_category_page1/goodsModule/goodsDetails", itemUrlSuffix.substring(1));
            qrCode = ImageIO.read(new URL(qrCodeUrl));
            qrCode = qrCode.getSubimage(102, 134, 1336, 1336);
        }

        if (qrCode != null) {
            String fileName = UUID.randomUUID() + ".png";
            InputStream inputStream = bufferedImageToInputStream(qrCode);
            result = fileStrategy.upload(fileName, inputStream, inputStream.available());
        }
        return result;
    }

    /**
     * 生成分享海报
     *
     * @param env 1-APP 2-微信小程序 3-H5 4-支付宝小程序
     */
    private InputStream genPoster(int env, String itemPicUrl, String shareHeadUrl,
                                  String shareName, String itemName, Double itemPrice,
                                  String itemUrlSuffix, String accessToken) throws Exception {
        int width = 640;
        int height = 1052;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.OPAQUE);
        g2d.dispose();
        g2d = image.createGraphics();

        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, width, height);

        g2d.setColor(new Color(182, 249, 225));
        g2d.setStroke(new BasicStroke(1));
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        BufferedImage proImg = readImgInner(itemPicUrl);
        g2d.drawImage(proImg.getScaledInstance(600, 600, Image.SCALE_SMOOTH), 20, 0, null);

        //填写售价
        g2d.setPaint(Color.RED);
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.BOLD, 30));
        g2d.drawString("￥" + itemPrice.toString(), 50, 600);

        BufferedImage shareHeadImg = readImgInner(shareHeadUrl);

        int baseHeight = 600;

        //xxx分享了一个商品
        g2d.setPaint(Color.GRAY);
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.PLAIN, 27));

        g2d.drawImage(shareHeadImg.getScaledInstance(120, 120, Image.SCALE_SMOOTH), 50, baseHeight, null);
        g2d.drawString(shareName + "分享了一个商品", 210, baseHeight + 60);

        //商品名称
        g2d.setPaint(Color.BLACK);
        final Font logoFont = new Font("Alibaba PuHuiTi R", Font.PLAIN, 21);
        g2d.setFont(logoFont);
        //填写售价
        g2d.setPaint(Color.BLACK);
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.BOLD, 25));
        drawWrappedText(g2d, itemName, 15, baseHeight + 150, width - 15);

        //画分割线
        g2d.setPaint(new Color(194, 194, 194));
        g2d.drawLine(10, baseHeight + 250, 630, baseHeight + 250);

        //生成底部文案 和 二维码
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.PLAIN, 17));
        BufferedImage qrCode = null;
        if (env == 1 || env == 2) {
            g2d.drawString("长按识别小程序", 30, baseHeight + 336);
            qrCode = WechatUtil.getWxacode(accessToken, "zwx_category_page1/goodsModule/goodsDetails" + itemUrlSuffix);
        } else if (env == 3) {
            g2d.drawString("扫描二维码购买商品", 30, baseHeight + 336);
            qrCode = QRCodeUtil.createImage(goodsUrl + itemUrlSuffix, null, true);
        } else if (env == 4) {
            g2d.drawString("长按识别小程序", 30, baseHeight + 336);
            String qrCodeUrl = alipayUtil.generateQrCode("zwx_category_page1/goodsModule/goodsDetails", itemUrlSuffix.substring(1));
            qrCode = ImageIO.read(new URL(qrCodeUrl));
            qrCode = qrCode.getSubimage(102, 134, 1336, 1336);
        }
        if (qrCode != null) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.drawImage(qrCode.getScaledInstance(160, 160, Image.SCALE_SMOOTH), 448, baseHeight + 266, null);
        }

        g2d.dispose();
        return bufferedImageToInputStream(image);
    }

    public static void drawWrappedText(Graphics g, String text, int x, int y, int maxWidth) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int lineHeight = metrics.getHeight();
        int currentX = x;
        int currentY = y;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String word = String.valueOf(c);
            int wordWidth = metrics.stringWidth(word);
            if (currentX + wordWidth > maxWidth) {
                currentX = x;
                currentY += lineHeight;
            }
            g.drawString(word, currentX, currentY);
            currentX += wordWidth;
        }
    }

    private BufferedImage readImgInner(String imageUrl) throws Exception {
        if (StringUtils.isBlank(imageUrl)) {
            throw new CoBusinessException(CoReturnFormat.SYS_RUNTIME_ERROR, "图片资源链接不能为空!");
        }
        if (imageUrl.endsWith(".webp")) {
            // Obtain a WebP ImageReader instance
            ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

            // Configure decoding parameters
            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);

            // Configure the input on the ImageReader
            reader.setInput(ImageIO.createImageInputStream(new URL(imageUrl).openStream()));

            // Decode the image
            return reader.read(0, readParam);
        } else {
            BufferedImage read = ImageIO.read(new URL(imageUrl));
            if (read != null) {
                return read;
            }
            throw new CoBusinessException(CoReturnFormat.SYS_NULL_ERROR, "下载资源" + imageUrl + "存在错误.");
        }
    }

    /**
     * 将BufferedImage转换为InputStream
     */
    public InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new CoBusinessException(CoReturnFormat.SYS_RUNTIME_ERROR, e);
        }
    }

    private ProductDetail assembleActivityInfoForRealInfo(ProductDetail detail, CereBuyerUser user, ProductParam param) {
        Long skuId = detail.getSkuId();

        //其它活动可能已经设置了该参数，所以这里做一下null判断
        Long memberLevelId = 0L;

        if (detail.getMap() == null) {
            //查询该商品所有组合规格数据封装到map
            List<Sku> skus = cereSkuMemberRealInfoDAO.findSkuListByProductId(param.getProductId(), memberLevelId);
            if (!EmptyUtils.isEmpty(skus)) {
                Map<String, Sku> map = new HashMap<>();
                String image = detail.getImage();
                for (Sku sku : skus) {
                    if (EmptyUtils.isEmpty(sku.getImage())) {
                        //当前规格没有图片，就设置默认图片为商品图片
                        sku.setImage(image);
                    }
                    //查询规格所有规格值拼接字符串
                    if (EmptyUtils.isEmpty(sku.getValueCodes())) {
                        sku.setValueCodes("单款项");
                    }
                    if (!sku.getSkuId().equals(skuId)) {
                        //传过来的规格id不是当前查到的skuId
                    } else {
                        //skuId匹配了
                        //detail对应的sku如果是活动就查的活动的库存
                        sku.setStockNumber(detail.getSurplusNumber());
                        sku.setTotal(detail.getTotal());
                    }
                    map.put(sku.getValueCodes(), sku);
                }
                detail.setMap(map);
            }
        }

        return detail;
    }

}

