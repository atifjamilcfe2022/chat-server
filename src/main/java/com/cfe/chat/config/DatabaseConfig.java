package com.cfe.chat.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

//    @Bean
//    public PlatformTransactionManager datasourceTransactionManager(final HikariDataSource hikariDataSource){
//        return new DataSourceTransactionManager(hikariDataSource);
//    }
}
