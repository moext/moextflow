package com.moext.flowservice.data.gen.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.moext.flowservice.data.gen.model.TaskComment;
import com.moext.flowservice.data.gen.model.TaskCommentExample;

public interface TaskCommentMapper {
	long countByExample(TaskCommentExample example);

	int deleteByExample(TaskCommentExample example);

	int deleteByPrimaryKey(Long id);

	int insert(TaskComment record);

	int insertSelective(TaskComment record);

	List<TaskComment> selectByExampleWithBLOBsWithRowbounds(TaskCommentExample example, RowBounds rowBounds);

	List<TaskComment> selectByExampleWithBLOBs(TaskCommentExample example);

	List<TaskComment> selectByExampleWithRowbounds(TaskCommentExample example, RowBounds rowBounds);

	List<TaskComment> selectByExample(TaskCommentExample example);

	TaskComment selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") TaskComment record, @Param("example") TaskCommentExample example);

	int updateByExampleWithBLOBs(@Param("record") TaskComment record, @Param("example") TaskCommentExample example);

	int updateByExample(@Param("record") TaskComment record, @Param("example") TaskCommentExample example);

	int updateByPrimaryKeySelective(TaskComment record);

	int updateByPrimaryKeyWithBLOBs(TaskComment record);

	int updateByPrimaryKey(TaskComment record);
}