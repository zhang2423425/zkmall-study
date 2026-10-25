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

/**
 * cere_order_return 退货单信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereOrderReturn implements Serializable {
    /**
     * 退货单id
     */
    @TableId(type = IdType.AUTO)
    private Long returnId;

    /**
     * 退货单号
     */
    private String returnFormid;

    /**
     * 关联订单id
     */
    private Long orderId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
