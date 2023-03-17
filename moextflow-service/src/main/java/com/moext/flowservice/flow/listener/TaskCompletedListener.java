package com.moext.flowservice.flow.listener;

import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moext.flowservice.data.gen.model.TodoTask;
import com.moext.flowservice.service.TodoTaskService;
import com.moext.flowservice.util.SpringUtils;

/**
 * 任务完成时监听器
 * @author PengPeng
 */
public class TaskCompletedListener implements FlowableEventListener { 

	private static Logger logger = LoggerFactory.getLogger(TaskCompletedListener.class);
	
	public RuntimeService getRuntimeService() {
		return SpringUtils.getBean(RuntimeService.class);
	}
	
	private TodoTaskService getTodoTaskService() {
		return SpringUtils.getBean(TodoTaskService.class);
	}
	
	@Override
	public void onEvent(FlowableEvent event) {
		FlowableEntityEvent entityEvent = (FlowableEntityEvent)event;
		Object entity = entityEvent.getEntity();
		if(entity instanceof TaskEntity) {//用户任务类型的实体
			TaskEntity taskEntity = (TaskEntity)entityEvent.getEntity();
			String procInsId = taskEntity.getProcessInstanceId();
			logger.info("TaskCompletedListener procId={} taskId={} onEvent...", procInsId, taskEntity.getId());
			
			ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().includeProcessVariables().processInstanceId(procInsId).singleResult();
			if(processInstance == null) {
				return;
			}
			
			if(StringUtils.isNotBlank(taskEntity.getAssignee())){//任务处理人为单人，待办改为已办
				if(!taskEntity.getAssignee().startsWith("{")) {
					TodoTask todoTask = new TodoTask();
					todoTask.setProcessInstanceId(procInsId);
					todoTask.setTaskId(taskEntity.getId());
					todoTask.setProcDefKey(processInstance.getProcessDefinitionKey());
					todoTask.setReceiveUserCode(taskEntity.getAssignee());
					getTodoTaskService().updateWhenTaskComplete(todoTask);
				}
			}else {
				logger.warn("no assignee record found taskId:{}", taskEntity.getId());
			}
		}
	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

	@Override
	public boolean isFireOnTransactionLifecycleEvent() {
		return false;
	}

	@Override
	public String getOnTransaction() {
		return null;
	}
}
