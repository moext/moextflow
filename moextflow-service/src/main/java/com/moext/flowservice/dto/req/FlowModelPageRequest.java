package com.moext.flowservice.dto.req;

import com.moext.flowservice.common.PageRequest;

public class FlowModelPageRequest extends PageRequest {

	private static final long serialVersionUID = -6975080699121968395L;
	
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
