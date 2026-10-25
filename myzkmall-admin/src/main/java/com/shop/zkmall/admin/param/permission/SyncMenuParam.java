/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.param.permission;

import com.shop.zkmall.commons.domain.permission.CerePlatformPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 同步菜单的参数
 *
 * @author zkmall zkmallvip@163.com
 */
@Data
@ApiModel(value = "同步菜单的参数")
public class SyncMenuParam {

    @Schema(description = "同步范围 true-同步所有商家 false-同步指定商家")
    private boolean syncAll;

    @Schema(description = "是否删除菜单")
    private boolean delMenu;

    @Schema(description = "指定商家账号")
    private String syncBusinessUsername;

    @Schema(description = "需要同步的菜单列表")
    private List<CerePlatformPermission> syncMenu;

}
