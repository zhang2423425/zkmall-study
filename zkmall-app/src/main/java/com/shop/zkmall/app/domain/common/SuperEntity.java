/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author zkmall zkmallvip@163.com
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SuperEntity<T> implements Serializable {
    public static final String FIELD_ID = "id";
    public static final String CREATE_TIME = "createTime";
    public static final String CREATE_TIME_COLUMN = "create_time";
    public static final String CREATE_USER = "createUser";
    public static final String CREATE_USER_COLUMN = "create_user";

    @Schema(description = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    protected T id;

    @Schema(description = "创建时间")
    protected LocalDateTime createTime;

    @Schema(description = "创建人ID")
    protected T createUser;

    /**
     * 保存和缺省验证组
     */
    public interface Save extends Default {

    }

    /**
     * 更新和缺省验证组
     */
    public interface Update extends Default {

    }
}
