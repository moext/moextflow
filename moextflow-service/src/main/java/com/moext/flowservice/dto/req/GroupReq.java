package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 用户组ID请求
 * @author PengPeng
 */
public class GroupReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	//角色标识
	@NotBlank(message="id不能为空!")
	private String id;
	
	//角色类型
	@NotBlank(message="type不能为空!")
	private String type;
	
	//角色名称
	@NotBlank(message="name不能为空!")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
