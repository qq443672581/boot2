package cn.dlj1.boot2.component.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@Configuration
public class MysqlDataSource {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties1() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource1() {
        return dataSourceProperties1().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource2")
    public DataSourceProperties dataSourceProperties2() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource2() {
        return dataSourceProperties2().initializeDataSourceBuilder().build();
    }


}
