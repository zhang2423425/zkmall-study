/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_visit 店铺访问信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereShopVisit implements Serializable {
    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 访问时间
     */
    private String visitTime;

    /**
     * 访问终端  1-APP 2-小程序
     */
    private Integer terminal;

    /**
     * 操作系统1-Android 2-IOS
     */
    @TableField("`system`")
    private Integer system;

    /**
     * 所在地区
     */
    private String city;

}
