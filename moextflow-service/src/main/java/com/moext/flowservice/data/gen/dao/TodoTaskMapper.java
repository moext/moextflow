package com.moext.flowservice.data.gen.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.data.gen.model.TodoTaskExample;

public interface TodoTaskMapper {
	long countByExample(TodoTaskExample example);

	int deleteByExample(TodoTaskExample example);

	int deleteByPrimaryKey(Long id);

	int insert(TodoTask record);

	int insertSelective(TodoTask record);

	List<TodoTask> selectByExampleWithRowbounds(TodoTaskExample example, RowBounds rowBounds);

	List<TodoTask> selectByExample(TodoTaskExample example);

	TodoTask selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") TodoTask record, @Param("example") TodoTaskExample example);

	int updateByExample(@Param("record") TodoTask record, @Param("example") TodoTaskExample example);

	int updateByPrimaryKeySelective(TodoTask record);

	int updateByPrimaryKey(TodoTask record);
}