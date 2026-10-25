/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.dao.after;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.zkmall.commons.domain.after.CerePlatformAfter;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zkmall zkmallvip@163.com
 */
@Mapper
public interface CerePlatformAfterDAO extends BaseMapper<CerePlatformAfter> {

    int insertSelective(CerePlatformAfter record);

    void updateAfter(CerePlatformAfter cerePlatformAfter);

}
