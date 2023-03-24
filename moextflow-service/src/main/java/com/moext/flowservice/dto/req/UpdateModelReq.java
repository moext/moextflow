package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotNull;

import com.moext.flowservice.common.BaseRequest;

public class UpdateModelReq extends BaseRequest {

	private static final long serialVersionUID = -3930557257825375648L;

	private String id;
	private String category;

	@NotNull(message = "id不能为空!")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotNull(message = "category不能为空!")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
