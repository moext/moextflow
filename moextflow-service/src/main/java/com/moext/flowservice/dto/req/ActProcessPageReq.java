package com.moext.flowservice.dto.req;

import java.util.Date;

import com.moext.flowservice.common.PageRequest;

public class ActProcessPageReq extends PageRequest {
	
	private static final long serialVersionUID = 8595820604956858365L;
	
	//流程分类
	private String category;
	//流程名称
	private String processName;
	//流程发起人
	private String processUser;
	private Date beginDate;
	private Date endDate;
	private String procInsId; 
	private String procDefKey;
	private String taskId;
	private String taskName;
	//当前任务处理人
	private String currentTaskUser;
	private String procDefId;
	
	//active=激活，suspend=挂起
	private String state;
	
	private String deploymentId;
	
	private String reason;
	
	private String processTitle;
	private String applyUserId;
	
	public String getProcessTitle() {
		return processTitle;
	}

	public void setProcessTitle(String processTitle) {
		this.processTitle = processTitle;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getCurrentTaskUser() {
		return currentTaskUser;
	}

	public void setCurrentTaskUser(String currentTaskUser) {
		this.currentTaskUser = currentTaskUser;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}