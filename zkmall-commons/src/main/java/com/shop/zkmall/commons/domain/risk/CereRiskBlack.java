/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.risk;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 黑名单表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("cere_risk_black")
@ApiModel(value = "CereRiskBlack", description = "黑名单表")
@AllArgsConstructor
public class CereRiskBlack implements Serializable {

    @Schema(description = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类型 1：ip  2:用户
     */
    @Schema(description = "类型 1：ip  2:用户")
    @NotNull(message = "类型 1：ip  2:用户不能为空")
    @TableField("type")
    private Integer type;

    /**
     * ip地址
     */
    @Schema(description = "ip地址")
    @Length(max = 100, message = "ip地址长度不能超过100")
    @TableField(value = "ip", condition = LIKE)
    private String ip;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @TableField("buyer_user_id")
    private Long buyerUserId;

    /**
     * 是否有效，0否 1是
     */
    @Schema(description = "是否有效，0否 1是")
    @TableField("state")
    private Boolean state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private String updateTime;

}
