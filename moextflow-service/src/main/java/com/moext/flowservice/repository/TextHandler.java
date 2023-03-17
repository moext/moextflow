package com.moext.flowservice.repository;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TextHandler implements TypeHandler<Object> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
	    if(parameter == null){
	    	ps.setString(i, null);
	    	return;
	    }	
	    ps.setString(i, parameter.toString());
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		String text = rs.getString(columnIndex);
	    return text;
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		 String text = cs.getString(columnIndex);
		 return text;
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		String text = rs.getString(columnName);
	    return text;
	}
}
