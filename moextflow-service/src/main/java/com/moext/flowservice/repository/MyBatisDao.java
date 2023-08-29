package com.moext.flowservice.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBatisDao extends SqlSessionTemplate {

	@Autowired
	public MyBatisDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	public <T> List<T> select(String statement, Object object, Class<T> c) {
		return selectList(statement, object);
	}

	public Integer count(String statement, Object parameter) {
		return super.selectOne(statement, parameter);
	}
}
