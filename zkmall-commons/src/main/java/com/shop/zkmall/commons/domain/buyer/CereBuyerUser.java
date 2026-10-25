/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.buyer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_user 客户信息实体类
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "CereBuyerUser", description = "客户信息实体类")
public class CereBuyerUser implements Serializable {

    /**
     * 客户id
     */
    @TableId(type = IdType.AUTO)
    private Long buyerUserId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String name;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 生日
     */
    @Schema(description = "生日")
    private String birthday;

    /**
     * 微信openID
     */
    private String wechatOpenId;

    /**
     * 微信unionId
     */
    private String wechatUnionId;

    /**
     * 微信昵称
     */
    private String wechatName;

    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 支付宝小程序用户id
     */
    private String aliUserId;

    /**
     * 注册手机号
     */
    @Schema(description = "注册手机号")
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像图片
     */
    @Schema(description = "头像图片")
    private String headImage;

    /**
     * 是否启用   1-是 0-否
     */
    private Integer state;

    /**
     * 是否加入黑名单 1-是 0-否
     */
    private Integer ifBlack;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求token
     */
    private String token;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 会员等级名称
     */
    @TableField(exist = false)
    private String memberLevelName;

    /**
     * 成长值
     */
    private Integer growth;

    /**
     * 积分
     */
    private Integer credit;

    /**
     * 注册来源 0-未知 1-APP 2-小程序 3-H5 4-支付宝小程序 5-PC
     */
    @Schema(description = "注册来源 0-未知 1-APP 2-小程序 3-H5 4-支付宝小程序 5-PC")
    private Integer terminal;

    /**
     * 下一级需要的成长值
     */
    @TableField(exist = false)
    private Integer nextLevelGrowth;

    /**
     * 下一级会员等级名称
     */
    @TableField(exist = false)
    private String nextLevelName;

    @Schema(description = "注册时的ip")
    private String registerIp;

    @Schema(description = "上次登录的ip")
    private String lastLoginIp;

    @Schema(description = "渠道编码")
    private String channelCode;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
