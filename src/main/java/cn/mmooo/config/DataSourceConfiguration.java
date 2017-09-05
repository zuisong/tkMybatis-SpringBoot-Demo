package cn.mmooo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

import static cn.mmooo.config.DynamicDataSourceHolder.MASTER_DATA_SOURCE;
import static cn.mmooo.config.DynamicDataSourceHolder.SLAVE_DATA_SOURCE;

/**
 * 多数据库源配置
 */
@Configuration
@EnableTransactionManagement(order = 10)
@Slf4j
public class DataSourceConfiguration {
    private static final String MASTER_DATA_SOURCE_PREFIX = "spring.datasource.druid.master";
    private static final String SLAVE_DATA_SOURCE_PREFIX = "spring.datasource.druid.slave";


    @Bean
    public Interceptor writeSqlInterceptor() {
        log.info("------ 初始化Sql拦截器 ------");
        return new WriteSqlInterceptor();

    }

    @Bean
    @ConfigurationProperties(MASTER_DATA_SOURCE_PREFIX)
    public DruidDataSource masterDataSource() {
        log.info("------ 初始化 Druid 主数据源 ------");
        return DruidDataSourceBuilder.create().build();

    }

    @Bean
    @ConfigurationProperties(SLAVE_DATA_SOURCE_PREFIX)
    public DruidDataSource slaveDataSource() {
        log.info("------ 初始化 Druid 从数据源 ------");
        return DruidDataSourceBuilder.create().build();

    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DruidDataSource masterDataSource, DruidDataSource slaveDataSource) {
        log.info("------ 初始化 Dynamic 数据源 ------");
        val targetDataSources = new HashMap<String, DataSource>();
        targetDataSources.put(MASTER_DATA_SOURCE, masterDataSource);
        targetDataSources.put(SLAVE_DATA_SOURCE, slaveDataSource);
        return new DynamicDataSource(slaveDataSource, targetDataSources);
    }
}
