package com.moext.flowservice.dto.req;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.moext.flowservice.common.BaseRequest;
import com.moext.flowservice.data.gen.model.TodoTaskExample;

/**
 * 待办列表分页统计请求
 * @author PengPeng
 */
public class TodoListCountReq extends BaseRequest {
	
	private static final long serialVersionUID = -997837390616701327L;

	@NotBlank(message = "接收人用户名不能为空")
    private String receiveUserCode;

	//状态
    private String status;

    //任务创建时间范围开始
    private Date taskCreateTimeBegin;
    
    //任务创建时间范围结束
    private Date taskCreateTimeEnd;

    //任务名，模糊匹配
    private String taskName;

	public String getReceiveUserCode() {
		return receiveUserCode;
	}

	public void setReceiveUserCode(String receiveUserCode) {
		this.receiveUserCode = receiveUserCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTaskCreateTimeBegin() {
		return taskCreateTimeBegin;
	}

	public void setTaskCreateTimeBegin(Date taskCreateTimeBegin) {
		this.taskCreateTimeBegin = taskCreateTimeBegin;
	}

	public Date getTaskCreateTimeEnd() {
		return taskCreateTimeEnd;
	}

	public void setTaskCreateTimeEnd(Date taskCreateTimeEnd) {
		this.taskCreateTimeEnd = taskCreateTimeEnd;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    
	public TodoTaskExample genExample() {
		TodoTaskExample example = new TodoTaskExample();
		TodoTaskExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(this.receiveUserCode)) {
			criteria.andReceiveUserCodeEqualTo(this.receiveUserCode);
		}
		if(StringUtils.isNotBlank(this.status)) {
			criteria.andStatusEqualTo(this.status);
		}
		if(StringUtils.isNotBlank(this.taskName)) {
			criteria.andTaskNameLike(this.taskName);
		}
		if(this.taskCreateTimeBegin != null) {
			criteria.andTaskCreateTimeGreaterThanOrEqualTo(this.taskCreateTimeBegin);
		}
		if(this.taskCreateTimeEnd != null) {
			criteria.andTaskCreateTimeLessThanOrEqualTo(this.taskCreateTimeEnd);
		}
		criteria.andIsDelEqualTo(false);
		return example;
	}
}
