/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_product 店铺商品信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereRecommendProduct implements Serializable {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long productId;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 推荐类型
     */
    private Integer recommendType;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
