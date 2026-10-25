/*
 * Copyright (C) 2017-2025
 * ZKMall All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.zkmall.admin.interceptor;


import com.shop.zkmall.admin.utils.ContextUtil;
import com.shop.zkmall.commons.constant.CoReturnFormat;
import com.shop.zkmall.commons.exception.CoBusinessException;
import com.shop.zkmall.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author zkmall zkmallvip@163.com
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Slf4j
@Component
public class ExecutorInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) target;

        BoundSql boundSql = statementHandler.getBoundSql();
        String name = invocation.getMethod().getName();
        log.info(" intercept name {}", name);

        String trimSql = boundSql.getSql().trim().toLowerCase();
        String uri = StringUtils.trimToEmpty(ContextUtil.getUri());
        //过滤掉登录请求
        if (trimSql.contains("delete from cere_redis_key where redis_key=")
                || trimSql.contains("insert into cere_redis_key (redis_key, end_time)")
                || trimSql.contains("into cere_platform_log")
                || trimSql.contains("into cere_platform_web_log")
                || "/canvas/examine".equals(uri)
                || "/check/handle".equals(uri)) {
            //直接释放
            return invocation.proceed();
        }
        if ((trimSql.startsWith("update")
                || trimSql.startsWith("insert")
                || trimSql.startsWith("delete"))
                && ContextUtil.getAdmin()) {
            throw new CoBusinessException(CoReturnFormat.DEMON_ACCOUNT_NOT_PERMITTED);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,
                this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
