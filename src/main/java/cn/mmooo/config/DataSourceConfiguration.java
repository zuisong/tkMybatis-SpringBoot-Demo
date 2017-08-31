package cn.mmooo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据库源配置
 */
@Configuration
@EnableTransactionManagement(order = 10)
@Slf4j
public class DataSourceConfiguration {
    private static final String MASTER_DATA_SOURCE_PREFIX = "spring.datasource.druid.master";
    private static final String SLAVE_DATA_SOURCE_PREFIX = "spring.datasource.druid.slave";


    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(MASTER_DATA_SOURCE_PREFIX)
    public DruidDataSource masterDataSource() {
        log.info("------ 初始化 Druid 主数据源 ------");
        return DruidDataSourceBuilder.create().build();

    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(SLAVE_DATA_SOURCE_PREFIX)
    public DruidDataSource slaveDataSource() {
        log.info("------ 初始化 Druid 从数据源 ------");
        return DruidDataSourceBuilder.create().build();

    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DruidDataSource masterDataSource, DruidDataSource slaveDataSource) {
        log.info("------ 初始化 Dynamic 数据源 ------");
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceHolder.MASTER_DATA_SOURCE, masterDataSource);
        targetDataSources.put(DynamicDataSourceHolder.SLAVE_DATA_SOURCE, slaveDataSource);
        return new DynamicDataSource(slaveDataSource, targetDataSources);
    }


}
