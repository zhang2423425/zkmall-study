/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.commons.domain.customer_service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
 * 客服表
 * </p>
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("cere_customer_service")
@ApiModel(value = "CereCustomerService", description = "客服表")
@AllArgsConstructor
public class CereCustomerService implements Serializable {

    /*@Schema(description = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;*/

    @Schema(description = "客服id,联系客服专用")
    private String kfId;

    @Schema(description = "开放客服id,调用接口专用")
    private String openKfId;

    /*@Schema(description = "店铺id")
    @TableField("shop_id")
    private Long shopId;*/

    /**
     * 客服名称
     */
    @Schema(description = "客服名称")
    @NotEmpty(message = "客服名称不能为空")
    @Length(max = 128, message = "客服名称长度不能超过128")
    @TableField(value = "name", condition = LIKE)
    private String name;

    /**
     * 客服头像
     */
    @Schema(description = "客服头像")
    @NotEmpty(message = "客服头像不能为空")
    @Length(max = 512, message = "客服头像长度不能超过512")
    @TableField(value = "head_img", condition = LIKE)
    private String headImg;

    /**
     * 客服链接
     */
    @Schema(description = "客服链接")
    @NotEmpty(message = "客服链接不能为空")
    @Length(max = 512, message = "客服链接长度不能超过512")
    @TableField(value = "url", condition = LIKE)
    private String url;

    /**
     * 状态 1-上线 0-下线
     */
    @Schema(description = "状态 1-上线 0-下线")
    @NotNull(message = "状态 1-上线 0-下线不能为空")
    @TableField("state")
    private Integer state;

    /*
    @Schema(description = "创建时间")
    @TableField("create_time")
    private String createTime;

    @Schema(description = "修改时间")
    @TableField("update_time")
    private String updateTime;
    */

}
