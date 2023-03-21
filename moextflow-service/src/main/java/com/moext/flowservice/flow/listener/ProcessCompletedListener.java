package com.moext.flowservice.flow.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.event.impl.FlowableProcessEventImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moext.flowservice.flow.callback.CallbackRequest;
import com.moext.flowservice.flow.callback.ProcessCompleteCallback;
import com.moext.flowservice.service.TodoTaskService;
import com.moext.flowservice.util.SpringUtils;


/**
 * 流程完成时监听器
 * @author PengPeng
 */
public class ProcessCompletedListener implements FlowableEventListener {

	private static Logger logger = LoggerFactory.getLogger(ProcessCompletedListener.class);
	
	public RuntimeService getRuntimeService() {
		return SpringUtils.getBean(RuntimeService.class);
	}
	
	private TodoTaskService getTodoTaskService() {
		return SpringUtils.getBean(TodoTaskService.class);
	}
	
	private ProcessCompleteCallback getProcessCompleteCallback() {
		return SpringUtils.getBean(ProcessCompleteCallback.class);
	}
	
	@Override
	public void onEvent(FlowableEvent event) {
		FlowableEntityEvent entityEvent = (FlowableEntityEvent)event;
		if(entityEvent instanceof FlowableProcessEventImpl) {
			FlowableProcessEventImpl flowableProcessEvent = (FlowableProcessEventImpl) entityEvent;
			String procInsId = flowableProcessEvent.getProcessInstanceId();
			logger.info("ProcessCompletedListener procId={} onEvent...", procInsId);
			
			ProcessInstance processInstance = getRuntimeService().createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
			if(processInstance == null) {
				return;
			}
			
			//生成办结
			getTodoTaskService().updateWhenProcessComplete(procInsId);
			
			//调用回调方法
			CallbackRequest callbackRequest = new CallbackRequest();
			callbackRequest.setActivitiEntityEvent(entityEvent);
			callbackRequest.setFormInstanceId(processInstance.getBusinessKey());
			callbackRequest.setProcDefKey(processInstance.getProcessDefinitionKey());
			callbackRequest.setProcessInstanceId(procInsId);
			getProcessCompleteCallback().handle(callbackRequest);
		}
	}

	@Override
	public boolean isFailOnException() {
		return true;
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
