/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.service.message;

import java.util.Map;

/**
 * @author zkmall zkmallvip@163.com
 */
public interface CereMessageLogService {

    void save(String phone, String template, Map<String, String> map, String subject, String resultCode);

}
