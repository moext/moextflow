package com.moext.flowservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moext.flowservice.data.gen.dao.TaskCommentMapper;
import com.moext.flowservice.data.gen.model.TaskComment;
import com.moext.flowservice.data.gen.model.TaskCommentExample;
import com.moext.flowservice.service.TaskCommentService;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {

	@Autowired
	private TaskCommentMapper taskCommentMapper;
	
	@Override
    public List<TaskComment> listComments(String processInstId){
    	TaskCommentExample example = new TaskCommentExample();
    	example.createCriteria().andProcessInstanceIdEqualTo(processInstId).andIsDelEqualTo(false);
    	return taskCommentMapper.selectByExampleWithBLOBs(example);
    }
}
