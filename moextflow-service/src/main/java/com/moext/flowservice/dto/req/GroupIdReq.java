package com.moext.flowservice.dto.req;

import javax.validation.constraints.NotBlank;

import com.moext.flowservice.common.BaseRequest;

/**
 * 用户组ID请求
 * @author PengPeng
 */
public class GroupIdReq extends BaseRequest {

	private static final long serialVersionUID = 8850736417093054728L;
	
	@NotBlank(message="groupId不能为空!")
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
