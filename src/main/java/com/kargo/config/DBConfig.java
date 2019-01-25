package com.kargo.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DBConfig {




    @Value("${jdbc_driverClassName}")
    private String driverClassName;
    @Value("${jdbc_url}")
    private String url;
    @Value("${jdbc_username}")
    private String username;
    @Value("${jdbc_password}")
    private String password;

//    @Value("${jdbc_etlAds_url}")
//    private String etlUrl;
//    @Value("${jdbc_etlAds_username}")
//    private String eltUsername;
//    @Value("${jdbc_etlAds_password}")
//    private String etlPassword;




    @Bean(name = "dataSource")
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setMaxActive(200);
        ds.setInitialSize(1);
        ds.setMaxWait(60000);
        ds.setMinIdle(3);
        ds.setRemoveAbandoned(true);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setValidationQuery("SELECT 1");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setMaxOpenPreparedStatements(600);
        ds.setRemoveAbandonedTimeout(1800);
        ds.setLogAbandoned(true);
        Properties connectProperties = new Properties();
        connectProperties.put("clientEncoding", "UTF-8");
        ds.setConnectProperties(connectProperties);
        return ds;
    }


//    @Bean(name = "etlDataSource")
//    public DruidDataSource etlDataSource() throws SQLException {
//        DruidDataSource ds = new DruidDataSource();
//        ds.setDriverClassName(driverClassName);
//        ds.setUrl(etlUrl);
//        ds.setUsername(eltUsername);
//        ds.setPassword(etlPassword);
//        ds.setMaxActive(200);
//        ds.setInitialSize(1);
//        ds.setMaxWait(60000);
//        ds.setMinIdle(3);
//        ds.setRemoveAbandoned(true);
//        ds.setTimeBetweenEvictionRunsMillis(60000);
//        ds.setMinEvictableIdleTimeMillis(300000);
//        ds.setValidationQuery("SELECT 1");
//        ds.setTestWhileIdle(true);
//        ds.setTestOnBorrow(false);
//        ds.setTestOnReturn(false);
//        ds.setMaxOpenPreparedStatements(600);
//        ds.setRemoveAbandonedTimeout(1800);
//        ds.setLogAbandoned(true);
//        Properties connectProperties = new Properties();
//        connectProperties.put("clientEncoding", "UTF-8");
//        ds.setConnectProperties(connectProperties);
//        return ds;
//    }


}
