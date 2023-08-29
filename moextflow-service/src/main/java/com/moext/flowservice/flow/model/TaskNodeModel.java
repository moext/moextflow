package com.moext.flowservice.flow.model;

import java.util.Date;

/**
 * 任务节点模型
 * 
 * @author PengPeng
 */
public class TaskNodeModel {

	private String taskDefName;
	private String taskDefKey;
	private String asignee;

	private Date startTime;
	private Date endTime;
	private Long durationInMillis;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public String getAsignee() {
		return asignee;
	}

	public void setAsignee(String asignee) {
		this.asignee = asignee;
	}

	public String getTaskDefName() {
		return taskDefName;
	}

	public void setTaskDefName(String taskDefName) {
		this.taskDefName = taskDefName;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

}
