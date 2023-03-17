package com.moext.flowservice.dto.req;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.moext.flowservice.common.BaseRequest;

/**
 * 流程审批请求
 * @author PengPeng
 */
public class ApproveProcessReq  extends BaseRequest{

	private static final long serialVersionUID = 6387552176005117704L;

	//待办ID
	@NotBlank(message = "待办ID不能为空")
	private String todoId;
	
	//操作类型
	@NotBlank(message = "操作类型不能为空")
	private String operType;

	//审批意见
	@NotNull(message = "审批意见不能为null")
	private String comment;
	
	//变量
	private Map<String, Object> variables = new HashMap<String, Object>();

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
