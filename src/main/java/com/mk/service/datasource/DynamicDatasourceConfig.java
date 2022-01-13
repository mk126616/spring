package com.mk.service.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DynamicDatasourceConfig {
    @Bean
    public DataSource firstDataSource(){
        DataSource dataSource = null;
        Properties properties = new Properties();
        try {
        properties.load(DynamicDatasourceConfig.class.getClassLoader().getResource("jdbc.properties").openStream());

            dataSource =  DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.out.println("druid获取datasource失败");
        }
        return dataSource;
    }
}
