/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.app.aspect.risk;

import com.shop.zkmall.app.aspect.risk.rule.Evaluate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zkmall zkmallvip@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluateRegistry {

    /**
     * 所有要计算的风控规则
     */
    private List<Evaluate> evaluates = new ArrayList<>();

    /**
     * 加入风控规则
     */
    public void addEvaluate(Evaluate eval) {
        this.evaluates.add(eval);
    }

}
