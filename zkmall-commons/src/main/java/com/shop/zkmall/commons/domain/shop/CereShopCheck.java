/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.shop;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_check 店铺入驻处理信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereShopCheck implements Serializable {

    /**
     * 处理id
     */
    @TableId(type = IdType.AUTO)
    private Long checkId;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 入住处理 1-统一入驻 0-拒绝入驻
     */
    private Integer checkHandle;

    /**
     * 处理原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
