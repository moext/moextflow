package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 任务指派请求
 * @author PengPeng
 *
 */
public class ChangeTaskAssigneeReq extends BaseRequest{

	private static final long serialVersionUID = -3192777332838968900L;

	@NotBlank(message = "taskId不能为空")
	private String taskId;
	
	@NotBlank(message = "assignee不能为空")
	private String assignee;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
