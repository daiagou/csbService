package com.kargo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
//public class TransactionConfig implements TransactionManagementConfigurer {
public class TransactionConfig {
    @Autowired
    private DataSource dataSource;
//    @Resource(name="etlDataSource")
//    private DataSource etlDataSource;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean(name = "etlTransactionManager")
//    public PlatformTransactionManager etlTransactionManager() {
//        return new DataSourceTransactionManager(etlDataSource);
//    }

}