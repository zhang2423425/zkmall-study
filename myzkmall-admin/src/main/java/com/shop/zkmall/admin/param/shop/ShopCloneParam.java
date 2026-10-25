/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.shop;

import com.shop.zkmall.commons.config.RsaProperties;
import com.shop.zkmall.commons.utils.RsaUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 更新商家请求
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "ShopUpdateParam", description = "更新商家请求")
public class ShopCloneParam {

    /**
     * 商家用户id
     */
    @Schema(description = "商家用户id")
    private Long businessUserId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺账号
     */
    @Schema(description = "店铺账号")
    private String shopPhone;

    /**
     * 店铺密码
     */
    @Schema(description = "店铺密码")
    private String shopPassword;

    /**
     * 店铺负责人
     */
    @Schema(description = "店铺负责人")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @Schema(description = "负责人电话")
    private String chargePersonPhone;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAdress;

    /**
     * 生效日期
     */
    @Schema(description = "生效日期")
    private String effectiveDate;

    /**
     * 生效时限（年）
     */
    @Schema(description = "生效时限（年）")
    private Integer effectiveYear;

    /**
     * 合同状态 1-有效 0-无效
     */
    @Schema(description = "合同状态 1-有效 0-无效")
    private Integer contractState;

    /**
     * 审核直播间
     */
    @Schema(description = "审核直播间 1-开启 0-关闭")
    private Integer auditLive;

    /**
     * 审核直播间商品
     */
    @Schema(description = "审核直播间商品 1-开启 0-关闭")
    private Integer auditLiveProduct;

    @Schema(description = "要克隆的店铺列表")
    private List<ShopUpdateParam> cloneList;

    /**
     * RSA解密
     *
     * @return 解密对象
     */
    public ShopCloneParam decrypt() throws Exception {
        ShopCloneParam res = new ShopCloneParam();
        BeanUtils.copyProperties(this, res);
        res.setShopPassword(RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, this.shopPassword));
        return res;
    }
}
