/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_order_parent 父订单信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereOrderParent implements Serializable {
    /**
     * 父订单id
     */
    @TableId(type = IdType.AUTO)
    private Long parentId;

    /**
     * 父订单编号
     */
    private String parentFormid;

    /**
     * 所有子订单总价之和
     */
    private BigDecimal orderPrice;

    /**
     * 所有子订单物流费用之和
     */
    private BigDecimal logisticsPrice;

    /**
     * 所有子订单支付金额之和
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
