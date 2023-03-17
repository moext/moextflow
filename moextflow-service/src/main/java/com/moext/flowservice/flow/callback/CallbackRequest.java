package com.moext.flowservice.flow.callback;

import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;

/**
 * 回调请求
 * @author PengPeng
 */
public class CallbackRequest {

	private String processInstanceId;
	
	private String formInstanceId;
	
	private String procDefKey;
	
	private FlowableEntityEvent activitiEntityEvent;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getFormInstanceId() {
		return formInstanceId;
	}

	public void setFormInstanceId(String formInstanceId) {
		this.formInstanceId = formInstanceId;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public FlowableEntityEvent getActivitiEntityEvent() {
		return activitiEntityEvent;
	}

	public void setActivitiEntityEvent(FlowableEntityEvent activitiEntityEvent) {
		this.activitiEntityEvent = activitiEntityEvent;
	}
}
