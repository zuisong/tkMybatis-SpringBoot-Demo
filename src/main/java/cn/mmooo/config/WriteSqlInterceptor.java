package cn.mmooo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * SQL 拦截器
 * 拦截当载入读库时，禁止写入数据，拦截写的sql
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class WriteSqlInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {


        System.out.println("拦截器");

        /**
         * 处理 DELETE UPDATE 语句
         */
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        //if (ms.getSqlCommandType() == SqlCommandType.INSERT || ms.getSqlCommandType() == SqlCommandType.DELETE || ms.getSqlCommandType() == SqlCommandType.UPDATE)
            if (DynamicDataSourceHolder.SLAVE_DATA_SOURCE.equals(DynamicDataSourceHolder.get())) {
                throw new IllegalAccessException("非法访问，读库禁止写入方法执行");
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