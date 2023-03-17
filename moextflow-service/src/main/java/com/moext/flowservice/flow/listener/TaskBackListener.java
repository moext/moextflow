package com.moext.flowservice.flow.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moext.flowservice.flow.callback.CallbackRequest;
import com.moext.flowservice.flow.callback.TaskBackCallback;
import com.moext.flowservice.flow.constants.ProcessDefConstants;
import com.moext.flowservice.util.SpringUtils;

/**
 * 任务回退时监听器
 * @author PengPeng
 */
public class TaskBackListener implements FlowableEventListener {

	private static Logger logger = LoggerFactory.getLogger(TaskBackListener.class);
	
	public RuntimeService getRuntimeService() {
		return SpringUtils.getBean(RuntimeService.class);
	}
	
	private TaskBackCallback getTaskBackCallback() {
		return SpringUtils.getBean(TaskBackCallback.class);
	}
	
	@Override
	public void onEvent(FlowableEvent event) {
		FlowableEntityEvent entityEvent = (FlowableEntityEvent)event;
		Object entity = entityEvent.getEntity();
		if(entity instanceof TaskEntity) {//用户任务类型的实体
			TaskEntity taskEntity = (TaskEntity)entityEvent.getEntity();
			String procInsId = taskEntity.getProcessInstanceId();
			logger.info("TaskBackListener procId={} taskId={} onEvent...", procInsId, taskEntity.getId());
			
			ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().includeProcessVariables().processInstanceId(procInsId).singleResult();
			if(processInstance == null) {
				return;
			}
			
			//当任务回到发起任务时，调用回调方法
			if(ProcessDefConstants.TASK_DEF_KEY_APPLYTASK.equalsIgnoreCase(taskEntity.getTaskDefinitionKey())){//回到第一个任务节点
				CallbackRequest callbackRequest = new CallbackRequest();
				callbackRequest.setActivitiEntityEvent(entityEvent);
				callbackRequest.setFormInstanceId(processInstance.getBusinessKey());
				callbackRequest.setProcDefKey(processInstance.getProcessDefinitionKey());
				callbackRequest.setProcessInstanceId(procInsId);
				//调用回调方法
				getTaskBackCallback().handle(callbackRequest);
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
