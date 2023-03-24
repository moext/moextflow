package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotNull;

import com.moext.flowservice.common.BaseRequest;

/**
 * 模型创建请求
 * 
 * @author PengPeng
 */
public class ModelCreateReq extends BaseRequest{

	private static final long serialVersionUID = -4280587374533947991L;

	private String name;
	private String key;
	private String description;
	private String category;

	@NotNull(message = "name不能为空!")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message = "key不能为空!")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@NotNull(message = "description不能为空!")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull(message = "category不能为空!")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
