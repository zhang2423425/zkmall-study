/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.after;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_order_after  售后单信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereOrderAfter implements Serializable {
    /**
     * 售后单id
     */
    @TableId(type = IdType.AUTO)
    private Long afterId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 售后单号
     */
    private String afterFormid;

    /**
     * 售后状态 1-售后中 2-售后成功 3-售后关闭
     */
    private Integer afterState;

    /**
     * （货物状态）是否收到货 1-是 0-否
     */
    private Integer goodsState;

    /**
     * 售后类型  1-仅退款  2-退货退款
     */
    private Integer afterType;

    /**
     * 退款金额
     */
    private BigDecimal price;

    /**
     * 买家说明
     */
    @TableField(value = "`explain`")
    private String explain;

    /**
     * 退款原因
     */
    private String returnReason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商家处理留言
     */
    private String reason;

    /**
     * 图片地址（多个以逗号隔开）
     */
    private String image;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
