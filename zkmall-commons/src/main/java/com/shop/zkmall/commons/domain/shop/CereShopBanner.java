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
 * cere_shop_banner banner配置实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
public class CereShopBanner implements Serializable {
    /**
     * banner配置id
     */
    @TableId(type = IdType.AUTO)
    private Long bannerId;

    /**
     * 关联店铺id
     */
    private Long shopId;

    /**
     * 样式   1-留边  2-填充
     */
    private Integer bannerStyle;

    /**
     * banner图片地址（多张图片以逗号隔开）
     */
    private String bannerImage;

    /**
     * 跳转地址
     */
    private String bannerUrl;

    /**
     * 是否启用 1-是 0-否
     */
    private Integer state;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
