package com.moext.flowservice.component.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.moext.flowservice.component.TaskComponent;
import com.moext.flowservice.flow.constants.FlowVariableConstants;

/**
 * 任务组件的Flowable实现
 * @author PengPeng
 */
@Component
public class FlowableTaskComponent implements TaskComponent {

	private static Logger logger = LoggerFactory.getLogger(FlowableTaskComponent.class);
	
	@Autowired
	private TaskService taskService;
	
	@Override
	@Transactional
	public String completeFirstTask(String userName, String procInsId, String comment){
		Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
		if (task != null){
			taskService.setAssignee(task.getId(), userName);
			logger.info("complete task procId={} taskId={}", procInsId, task.getId());
			complete(task.getId(), procInsId, comment, null, null);
			return task.getId();
		}else {
			throw new IllegalArgumentException("流程实例ID为[" + procInsId + "]的记录不存在");
		}
	}
	
	@Override
	@Transactional
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars){
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// 设置流程变量
		if (vars == null){
			vars = new HashMap<String, Object>();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put(FlowVariableConstants.VARIABLE_TITLE, title);
		}
		
		// 提交任务
		taskService.complete(taskId, vars);
	}
	
	@Override
	public void setAssignee(String taskId, String userId){
		taskService.setAssignee(taskId, userId);
	}

	@Override
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars) {
		complete(taskId, procInsId, comment, null, vars);
	}

	@Override
	public Task getCurrentTask(String procInsId){
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInsId).includeProcessVariables().active().orderByTaskCreateTime().asc().list();
		if(CollectionUtils.isNotEmpty(taskList)) {
			return taskList.get(0);
		}else {
			return null;
		}
	}
}
