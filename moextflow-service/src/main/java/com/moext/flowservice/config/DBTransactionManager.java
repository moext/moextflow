package com.moext.flowservice.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DBTransactionManager {
	
    @Autowired
    private DataSource dataSource;
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager dstm = new DataSourceTransactionManager();
		dstm.setDataSource(dataSource);
		return dstm;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		ssfb.setDataSource(dataSource);
		return ssfb;
	}
}
