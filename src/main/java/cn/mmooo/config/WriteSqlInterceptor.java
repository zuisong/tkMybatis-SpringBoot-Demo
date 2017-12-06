package cn.mmooo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * SQL 拦截器
 * 拦截当载入读库时，禁止写入数据，拦截写的sql
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class WriteSqlInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("sql拦截器生效");
        /**
         * 处理 DELETE UPDATE 语句
         */
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        if (ms.getSqlCommandType() == SqlCommandType.INSERT || ms.getSqlCommandType() == SqlCommandType.DELETE
                || ms.getSqlCommandType() == SqlCommandType.UPDATE) {
            if (DynamicDataSourceHolder.SLAVE_DATA_SOURCE.equals(DynamicDataSourceHolder.get())) {
                throw new IllegalAccessException("非法访问，读库禁止写入方法执行");
            }
        }
        //测试时，Debug是启用的   允许测试方法直接调用Dao
        if (!log.isDebugEnabled()) {
            if (DynamicDataSourceHolder.get() == null) {
                throw new IllegalArgumentException("禁止在service层以外的地方调用dao");
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {

    }
}
