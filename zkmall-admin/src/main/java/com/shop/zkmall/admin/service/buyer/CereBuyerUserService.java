/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.buyer;

import com.shop.zkmall.admin.page.buyer.BuyerUser;
import com.shop.zkmall.admin.page.buyer.BuyerUserDetail;
import com.shop.zkmall.admin.param.buyer.*;
import com.shop.zkmall.admin.param.credit.UpdateCreditParam;
import com.shop.zkmall.admin.param.user.UserSearchParam;
import com.shop.zkmall.commons.domain.buyer.CereBuyerUser;
import com.shop.zkmall.commons.domain.common.Page;
import com.shop.zkmall.commons.domain.label.CerePlatformLabel;
import com.shop.zkmall.commons.domain.user.CerePlatformUser;

import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereBuyerUserService {
    /**
     * 分页查询客户
     */
    Page<BuyerUser> getAll(BuyerGetAllParam param);

    /**
     * 根据主键查询客户
     */
    BuyerUserDetail getById(BuyerGetByIdParam param);

    /**
     * 查询客户标签
     */
    List<CerePlatformLabel> getLabels(BuyerGetLabelsParam param);

    /**
     * 保存用户标签
     */
    void saveUserLabel(BuyerSaveUserLabelParam param, CerePlatformUser user);

    /**
     * 将客户加入/取消黑名单
     */
    void blacklist(BuyerBlackListParam param, CerePlatformUser user);

    /**
     * 条件查询标签
     */
    List<CerePlatformLabel> getUserLabels(BuyerGetLabelsParam param);

    /**
     * 增加/减少客户积分
     */
    void updateCredit(UpdateCreditParam param, CerePlatformUser user);

    /**
     * 搜索用户
     */
    Page<CereBuyerUser> searchUser(UserSearchParam param);
}
