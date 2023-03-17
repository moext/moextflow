package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 待办ID请求
 * @author PengPeng
 */
public class TodoIdReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	@NotBlank(message="todoId不能为空!")
	private String todoId;

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}
}
