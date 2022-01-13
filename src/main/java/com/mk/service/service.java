package com.mk.service;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class service {

    public void transactionTest() {
//        PlatformTransactionManager transactionManager = new DataSourceTransactionManager();
//        String a = "";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        da
    }
}
